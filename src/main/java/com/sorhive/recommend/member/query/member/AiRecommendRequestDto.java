package com.sorhive.recommend.member.query.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * <pre>
 * Class : AiRecommendRequestDto
 * Comment: AI 요청 데이터 전송 객체
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
@Getter
public class AiRecommendRequestDto {

    @JsonProperty
    List<AiRecommendDto> DB;

    protected AiRecommendRequestDto() {}
    public AiRecommendRequestDto(List<AiRecommendDto> aiRecommendDtos) {
        this.DB = aiRecommendDtos;
    }

}
