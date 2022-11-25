package com.sorhive.recommend.chatting.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * <pre>
 * Class : ChattingQueryService
 * Comment: 채팅 조회 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-18       부시연           최초 생성
 * 2022-11-18       부시연           자신의 채팅 목록 불러오기
 * 2022-11-19       부시연           자신과 채팅한 한 사람과의 내역 불러오기
 * 2022-11-21       부시연           채팅 목록 조회 기능 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class ChattingQueryService {

    private static final Logger log = LoggerFactory.getLogger(ChattingQueryService.class);
    private final MongoChattingQueryRepository mongoChattingQueryRepository;

    public ChattingQueryService(MongoChattingQueryRepository mongoChattingQueryRepository) {
        this.mongoChattingQueryRepository = mongoChattingQueryRepository;
    }

    /** 채팅 개수 조회하기 */
    public int findChattingCount(Long memberCode, Long memberCode2 ) {

        log.info("[ChattingQueryService] Start ==============================");

        /* membercode1은 작은값 membercode 2는 큰값으로 맞춘다. */
        if(memberCode > memberCode2) {
            Long temp = 0L;
            temp = memberCode;
            memberCode = memberCode2;
            memberCode2 = temp;
        }

        MongoChattingData mongoChattingData = mongoChattingQueryRepository.findFirstByMemberCode1AndMemberCode2(memberCode, memberCode2);

        if (mongoChattingData == null) {
            return 0;
        }

        return mongoChattingData.getMessages().size();
        
    }
}
