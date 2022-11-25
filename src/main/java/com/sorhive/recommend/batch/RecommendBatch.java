package com.sorhive.recommend.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sorhive.recommend.chatting.query.ChattingQueryService;
import com.sorhive.recommend.member.query.member.AiRecommendDto;
import com.sorhive.recommend.member.query.member.AiRecommendRequestDto;
import com.sorhive.recommend.member.query.member.MemberCodeData;
import com.sorhive.recommend.member.query.member.MemberQueryService;
import com.sorhive.recommend.mongorecommend.command.application.service.MongoRecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Class : RecommendBatch
 * Comment: 배치 잡을 생성 및 순차적으로 실행
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-23       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Configuration
public class RecommendBatch {
    private static final Logger log = LoggerFactory.getLogger(RecommendBatch.class);
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MemberQueryService memberQueryService;
    private final ChattingQueryService chattingQueryService;
    private final MongoRecommendService mongoRecommendService;
    private AiRecommendRequestDto aiRecommendRequestDto;
    private ResponseEntity<Map> response;
    @Value("${url.friend}")
    private String friendUrl;

    public RecommendBatch(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MemberQueryService memberQueryService, ChattingQueryService chattingQueryService, MongoRecommendService mongoRecommendService) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.memberQueryService = memberQueryService;
        this.chattingQueryService = chattingQueryService;
        this.mongoRecommendService = mongoRecommendService;
    }

    @Bean
    public Job recommendJob() {
        
        /*  batch job을 생성 */
        return jobBuilderFactory.get("recommendJob")
                
                /* bean으로 등록된 Step을 순차적으로 실행 */
                .start(recommendStep1(null))
                .next(recommendStep2(null))
                .next(recommendStep3(null))
                .build();
    }

    @Bean
    @JobScope
    public Step recommendStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        
        /* recommendStep1 batch step을 생성 */
        return stepBuilderFactory.get("recommendStep1")
                
                /* step 안에서 수행될 기능들을 명시, tasklet은 step 안에서 단일로 수행될 커스텀한 기능을 선언할 때 사용 */
                .tasklet((contribution, chunkContext) -> {
                    log.info("[recommendStep1] Start==================");
                    log.info("[recommendStep1] requestDate = {}",requestDate);
                    log.info("[recommendStep1] AI 서버와 통신하기 위해 객체에 정보 담기");

                    List<MemberCodeData> memberCodeDataList = memberQueryService.getAllMemberData();
                    List<AiRecommendDto> aiRecommendDtos = new ArrayList<>();

                    for (int i = 0; i < memberCodeDataList.size(); i++) {
                        List<MemberCodeData> guestCodeDataList = memberQueryService.getAllMemberWithOutMemberCodeData(memberCodeDataList.get(i).getMemberCode());
                        for (int j = 0; j < guestCodeDataList.size(); j++) {

                            AiRecommendDto aiRecommendDto = new AiRecommendDto(
                                    memberCodeDataList.get(i).getMemberCode(),
                                    guestCodeDataList.get(j).getMemberCode(),
                                    memberQueryService.countRoomVisit(memberCodeDataList.get(i).getMemberCode(), guestCodeDataList.get(j).getMemberCode()),
                                    memberQueryService.countLifingVisit(memberCodeDataList.get(i).getMemberCode(), guestCodeDataList.get(j).getMemberCode()),
                                    memberQueryService.countGuestBook(memberCodeDataList.get(i).getMemberCode(), guestCodeDataList.get(j).getMemberCode()),
                                    chattingQueryService.findChattingCount(memberCodeDataList.get(i).getMemberCode(), guestCodeDataList.get(j).getMemberCode())

                            );
                            aiRecommendDtos.add(aiRecommendDto);

                        }
                    }
                    aiRecommendRequestDto = new AiRecommendRequestDto(
                            aiRecommendDtos
                    );

                    log.info("[recommendStep1] End==================");
                    /* batch가 성공적으로 수행되고 종료됨을 반환 */
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step recommendStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("recommendStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("[recommendStep2] Start==================");
                    log.info("[recommendStep2] requestDate = {}",requestDate);
                    log.info("[recommendStep2] 담긴 객체를 통해 AI서버와 통신하여 추천 배열 받아오기");

                    /* AI서버에 전송하기 위해 차셋 등 헤더 설정하기 */
                    HttpHeaders headers = new HttpHeaders();
                    Charset utf8 = Charset.forName("UTF-8");
                    MediaType mediaType = new MediaType("application", "json", utf8);
                    headers.setContentType(mediaType);

                    /* JSON으로 파싱하기 */
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonString = objectMapper.writeValueAsString(aiRecommendRequestDto);
                    System.out.println(jsonString);

                    /* 헤더와 바디를 하나의 JSON으로 만들기 */
                    HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);

                    System.out.println(requestEntity);

                    RestTemplate restTemplate = new RestTemplate();

                    /* 스프링 3.0부터 지원하는 Spring의 HTTP 통신 템플릿 */
                    response = restTemplate.exchange(friendUrl, HttpMethod.PUT, requestEntity, Map.class);

                    log.info("[recommendStep2] End==================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step recommendStep3(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("recommendStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("[recommendStep3] Start==================");
                    log.info("[recommendStep3] requestDate = {}",requestDate);
                    log.info("[recommendStep3] 몽고 DB에 친구 추천 배열에 저장하기 ");

                    List<String> memberCodes = new ArrayList<>(response.getBody().keySet());
                    List<List<String>> guestCodes = new ArrayList<>(response.getBody().values());

                    /* 몽고 DB에 친추 추천 배열 저장하기 */
                    mongoRecommendService.createRecommend(memberCodes, guestCodes);
                    
                    log.info("[recommendStep3] End==================");

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
