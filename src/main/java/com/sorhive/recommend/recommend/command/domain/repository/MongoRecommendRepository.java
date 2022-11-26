package com.sorhive.recommend.recommend.command.domain.repository;

import com.sorhive.recommend.recommend.command.domain.model.MongoRecommend;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <pre>
 * Class : MongoRecommendRepository
 * Comment: 몽고 DB 친구 추천 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-24       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface MongoRecommendRepository extends MongoRepository<MongoRecommend, String> {
}
