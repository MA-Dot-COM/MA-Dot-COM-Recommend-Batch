package com.sorhive.recommend.batch;

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

    public RecommendBatch(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job recommendJob() {
        
        /*  batch job을 생성 */
        return jobBuilderFactory.get("recommendJob")
                
                /* bean으로 등록된 Step을 순차적으로 실행 */
                .start(recommendStep1(null))
                .next(recommendStep2(null))
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

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
