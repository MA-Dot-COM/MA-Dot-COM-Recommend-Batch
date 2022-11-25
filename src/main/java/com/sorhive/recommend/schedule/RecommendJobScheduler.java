package com.sorhive.recommend.schedule;

import com.sorhive.recommend.batch.RecommendBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Class : RecommendJobScheduler
 * Comment: 친구 추천에 대한 스케줄러
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
@Component
public class RecommendJobScheduler {

    private static final Logger log = LoggerFactory.getLogger(RecommendJobScheduler.class);
    private final JobLauncher jobLauncher;
    private final RecommendBatch recommendBatch;

    public RecommendJobScheduler(JobLauncher jobLauncher, RecommendBatch recommendBatch) {
        this.jobLauncher = jobLauncher;
        this.recommendBatch = recommendBatch;
    }

    // 10초에 한 번씩 실행
    @Scheduled(cron = "0/10 * * * * *")
    public void testJobSchedule() {
        // 파라미터를 매번 다르게 설정하여 다른 Job Instance로 지정
        Map<String, JobParameter> config = new HashMap<>();
        config.put("time", new JobParameter(System.currentTimeMillis()));

        JobParameters jobParameters = new JobParameters(config);
        try {
             JobExecution jobExecution = jobLauncher.run(recommendBatch.recommendJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            e.getMessage();
        }
    }
}
