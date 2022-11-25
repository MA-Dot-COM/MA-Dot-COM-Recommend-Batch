package com.sorhive.recommend.member.query.member;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * Class : MemberMapper
 * Comment: 멤버 매퍼
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
@Mapper
public interface RecommendMapper {
    List<MemberCodeData> findAllMember();
    List<MemberCodeData> findAllMemberIsNotMemberCode(Long memberCode);
    int countRoomVisit(Long userMemberCode, Long guestMemberCode);

    int countLifingVisit(Long userMemberCode, Long guestMemberCode);

    int countGuestBook(Long userMemberCode, Long guestMemberCode);
}
