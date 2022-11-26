package com.sorhive.recommend.recommend.infra;

import com.sorhive.recommend.recommend.command.domain.model.MongoRecommend;
import com.sorhive.recommend.recommend.command.domain.model.Recommend;
import com.sorhive.recommend.recommend.command.domain.repository.MongoRecommendRepository;
import com.sorhive.recommend.recommend.command.domain.repository.RecommendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * Class : RecommendInfraService
 * Comment: 친구 추천 인프라 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-27       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class RecommendInfraService {
    private static final Logger log = LoggerFactory.getLogger(RecommendInfraService.class);
    private final MongoRecommendRepository mongoRecommendRepository;
    private final RecommendRepository recommendRepository;

    public RecommendInfraService(MongoRecommendRepository mongoRecommendRepository, RecommendRepository recommendRepository) {
        this.mongoRecommendRepository = mongoRecommendRepository;
        this.recommendRepository = recommendRepository;
    }

    /** 친구추천 배열 저장하기 */
    @Transactional
    public void createRecommend(List<String> memberCodes, List<List<String>> guestCodes) {
        log.info("[RecommendInfraService] createRecommend Start ===================");
        log.info("[RecommendInfraService] memberCode " + memberCodes);
        log.info("[RecommendInfraService] memberCodeList " + guestCodes);

        MongoRecommend mongoRecommend = new MongoRecommend(
                memberCodes,
                guestCodes
        );

        mongoRecommendRepository.insert(mongoRecommend);

        /* 몽고 DB ID 값 저장하기*/
        Recommend recommend = new Recommend(
                mongoRecommend.getId()
        );
        
        recommendRepository.save(recommend);

        log.info("[RecommendInfraService] createRecommend End ===================");

    }
}
