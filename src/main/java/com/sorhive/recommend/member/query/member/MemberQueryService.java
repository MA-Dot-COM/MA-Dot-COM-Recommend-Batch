package com.sorhive.recommend.member.query.member;

import com.sorhive.recommend.member.NoMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Class : MemberQueryService
 * Comment: 멤버 조회용 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-23       부시연           최초 생성
 * 2022-11-24       부시연           전체 회원 목록 불러오기
 * 2022-11-24       부시연           해당 회원을 제외한 전체 회원 목록 불러오기
 * 2022-11-24       부시연           방 조회수 세기
 * 2022-11-24       부시연           라이핑 조회수 세기
 * 2022-11-24       부시연           방명록 작성수 세기
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
public class MemberQueryService {

    private static final Logger log = LoggerFactory.getLogger(MemberQueryService.class);
    private final RecommendMapper recommendMapper;

    public MemberQueryService(RecommendMapper recommendMapper) {
        this.recommendMapper = recommendMapper;
    }
    
    /** 전체 회원 목록 불러오기 */
    public List<MemberCodeData> getAllMemberData() {

        log.info("[MemberQueryService] getMemberData Start ================");

        List<MemberCodeData> memberCodeData = recommendMapper.findAllMember();

        if(memberCodeData == null) {
            throw new NoMemberException();
        }

        return memberCodeData;

    }

    /** 해당 회원을 제외한 전체 회원 목록 불러오기 */
    public List<MemberCodeData> getAllMemberWithOutMemberCodeData(Long memberCode) {

        log.info("[MemberQueryService] getMemberData Start ================");

        List<MemberCodeData> memberCodeData = recommendMapper.findAllMemberIsNotMemberCode(memberCode);

        if(memberCodeData == null) {
            throw new NoMemberException();
        }

        return memberCodeData;

    }

    /** 방 조회수 세기 */
    public int countRoomVisit(Long userMemberCode, Long guestMemberCode) {

        log.info("[MemberQueryService] getMemberData Start ================");

        int roomVisitCount = recommendMapper.countRoomVisit(userMemberCode, guestMemberCode);

        return roomVisitCount;

    }

    /** 라이핑 조회수 세기 */
    public int countLifingVisit(Long userMemberCode, Long guestMemberCode) {

        log.info("[MemberQueryService] getMemberData Start ================");

        int lifingVisitCount = recommendMapper.countLifingVisit(userMemberCode, guestMemberCode);

        return lifingVisitCount;

    }

    /** 방명록 작성수 세기 */
    public int countGuestBook(Long userMemberCode, Long guestMemberCode) {

        log.info("[MemberQueryService] getMemberData Start ================");

        int guestBookCount = recommendMapper.countGuestBook(userMemberCode, guestMemberCode);


        return guestBookCount;

    }

}
