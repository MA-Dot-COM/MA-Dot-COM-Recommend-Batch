package com.sorhive.recommend.chatting.query;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <pre>
 * Class : MongoChattingQueryRepository
 * Comment: 몽고 DB 채팅 조회 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-24       부시연           최초 생성
 * 2022-11-24       부시연           회원 1과 회원 2로 채팅을 업로드 순으로 조회
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface MongoChattingQueryRepository extends MongoRepository<MongoChattingData, String> {

    MongoChattingData findFirstByMemberCode1AndMemberCode2(Long memberCode, Long memberCode2);

}
