package com.sorhive.recommend.recommend.command.domain.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre>
 * Class : MongoRecommend
 * Comment: 몽고 DB 추천 데이터 도큐먼트
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
@Getter
@Document(collection = "recommend")
public class MongoRecommend {

    @Id
    private String id;
    private List<String> memberCodes;
    private List<List<String>> guestCodes;
    private LocalDateTime uploadTime;

    public MongoRecommend(List<String> memberCodes, List<List<String>> guestCodes) {
        this.memberCodes = memberCodes;
        this.guestCodes = guestCodes;
        this.uploadTime = LocalDateTime.now();
    }
}
