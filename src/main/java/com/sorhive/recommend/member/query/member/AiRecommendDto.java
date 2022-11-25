package com.sorhive.recommend.member.query.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * <pre>
 * Class : AiRecommendDto
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
public class AiRecommendDto {
    @JsonProperty
    private Long userId;
    @JsonProperty
    private Long guest;
    @JsonProperty
    private int lifingCount;
    @JsonProperty
    private int roomInCount;
    @JsonProperty
    private int guestBookCount;

    @JsonProperty
    private int chatting;

    protected AiRecommendDto(){}
    public AiRecommendDto(Long userId, Long guest, int countRoomVisit, int countLifingVisit, int countGuestBook, int chattingCount) {
        this.userId = userId;
        this.guest = guest;
        this.lifingCount = countLifingVisit;
        this.guestBookCount = countGuestBook;
        this.roomInCount = countRoomVisit;
        this.chatting = chattingCount;
    }

}
