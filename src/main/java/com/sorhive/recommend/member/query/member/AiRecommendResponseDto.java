package com.sorhive.recommend.member.query.member;

import lombok.Getter;

import java.util.Map;

/**
 * <pre>
 * Class : AiRecommendRequestDto
 * Comment: AI 추천 응답 전송 객체
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
public class AiRecommendResponseDto {

    Map<String, Object> map;

    protected AiRecommendResponseDto() {}

}
