package com.sorhive.recommend.recommend.command.domain.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : Recommend
 * Comment: 친구 추천 도메인
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
@Table(name = "tbl_recommends")
@Entity
public class Recommend {

    @Id
    @Column(name = "recommend_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendNo;

    @Column(name = "recommend_id")
    private String recommendId;

    @Column(name = "recommend_upload_time")
    private Timestamp uploadTime;

    protected Recommend() {}

    public Recommend(String recommendId) {
        this.recommendId = recommendId;
        this.uploadTime = new Timestamp(System.currentTimeMillis());
    }

}
