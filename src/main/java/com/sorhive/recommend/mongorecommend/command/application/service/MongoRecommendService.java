package com.sorhive.recommend.mongorecommend.command.application.service;

import com.sorhive.recommend.mongorecommend.command.domain.model.MongoRecommend;
import com.sorhive.recommend.mongorecommend.command.domain.repository.MongoRecommendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * Class : MongoRecommendService
 * Comment: 몽고 DB 친구 추천 배열 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-25       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class MongoRecommendService {
    private static final Logger log = LoggerFactory.getLogger(MongoRecommendService.class);
    private final MongoRecommendRepository mongoRecommendRepository;

    public MongoRecommendService(MongoRecommendRepository mongoRecommendRepository) {
        this.mongoRecommendRepository = mongoRecommendRepository;
    }

    /** 친구추천 배열 저장하기 */
    @Transactional
    public void createRecommend(List<String> memberCodes, List<List<String>> guestCodes) {
        log.info("[MongoRecommendService] createRecommend Start ===================");
        log.info("[MongoRecommendService] memberCode " + memberCodes);
        log.info("[MongoRecommendService] memberCodeList " + guestCodes);

        MongoRecommend mongoRecommend = new MongoRecommend(
                memberCodes,
                guestCodes
        );

        mongoRecommendRepository.insert(mongoRecommend);

        log.info("[MongoRecommendService] createRecommend End ===================");

    }
}
