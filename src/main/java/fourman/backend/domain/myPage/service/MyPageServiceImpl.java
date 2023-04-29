package fourman.backend.domain.myPage.service;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.entity.FreeBoardComment;
import fourman.backend.domain.freeBoard.entity.Recommendation;
import fourman.backend.domain.freeBoard.repository.FreeBoardCommentRepository;
import fourman.backend.domain.freeBoard.repository.FreeBoardRepository;
import fourman.backend.domain.freeBoard.repository.RecommendataionRepository;
import fourman.backend.domain.member.entity.*;
import fourman.backend.domain.member.repository.*;
import fourman.backend.domain.myPage.controller.requestForm.AddCafeCodeRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.AddPointRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.CafeInfoModifyRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.MyInfoModifyRequestForm;
import fourman.backend.domain.myPage.service.responseForm.*;
import fourman.backend.domain.noticeBoard.entity.NoticeBoard;
import fourman.backend.domain.noticeBoard.repository.NoticeBoardRepository;
import fourman.backend.domain.order.entity.OrderInfo;
import fourman.backend.domain.order.repository.OrderInfoRepository;
import fourman.backend.domain.questionboard.entity.Comment;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.repository.CommentRepository;
import fourman.backend.domain.questionboard.repository.QuestionBoardRepository;
import fourman.backend.domain.reservation.repository.ReservationRepository;
import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import fourman.backend.domain.reviewBoard.repository.ReviewBoardRepository;
import fourman.backend.domain.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    final private MemberRepository memberRepository;
    final private MemberProfileRepository memberProfileRepository;
    final private ReviewBoardRepository reviewBoardRepository;
    final private FreeBoardRepository freeBoardRepository;
    final private QuestionBoardRepository questionBoardRepository;
    final private CommentRepository commentRepository;
    final private FreeBoardCommentRepository freeBoardCommentRepository;
    final private RedisService redisService;
    final private CafeRepository cafeRepository;
    final private PointRepository pointRepository;
    final private CafeCodeRepository cafeCodeRepository;
    final private OrderInfoRepository orderInfoRepository;
    final private PointInfoRepository pointInfoRepository;
    final private RecommendataionRepository recommendataionRepository;
    final private FavoriteRepository favoriteRepository;

    @Override
    public MyInfoResponse myInfo(Long memberId) {
        Optional<Member> maybeMember  = memberRepository.findById(memberId);
        Optional<MemberProfile> maybeMemberProfile  = memberProfileRepository.findById(memberId);

        if (maybeMember.isEmpty() || maybeMemberProfile.isEmpty()) {
            log.info("읽을 수가 없드아!");
            return null;
        }

        Member member = maybeMember.get();
        MemberProfile memberProfile = maybeMemberProfile.get();

        MyInfoResponse myInfoResponse = new MyInfoResponse(
                member.getNickName(), member.getBirthdate(), member.getEmail(),
                memberProfile.getPhoneNumber(), memberProfile.getAddress()
        );

        return myInfoResponse;
    }

    @Override
    public MyInfoSideBarResponse myInfoSideBar(Long memberId) {
        Optional<Member> maybeMember  = memberRepository.findById(memberId);
        Member member = maybeMember.get();

        if (maybeMember.isEmpty()) {
            log.info("읽을 수가 없드아!");
            return null;
        }

        Optional<Point> maybePoint = pointRepository.findByMemberId(member);
        Long point;

        if(maybePoint.isEmpty()) {
            point = null;
        } else {
            point = maybePoint.get().getPoint();
        }


        MyInfoSideBarResponse myInfoSideBarResponse = new MyInfoSideBarResponse(
                member.getNickName(), member.getAuthority().getAuthorityName().getAUTHORITY_TYPE(), point
        );

        return myInfoSideBarResponse;
    }

    @Override
    public MyInfoModifyResponse myInfoModify(Long memberId, MyInfoModifyRequestForm modifyRequest) {
        // 기존 사용자 정보 조회
        Optional<Member> maybeMember = memberRepository.findById(memberId);
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findById(memberId);

        if (maybeMember.isEmpty() || maybeMemberProfile.isEmpty()) {
            log.info("읽을 수가 없드아!");
            return null;
        }

        // 멤버 프로필 수정
        MemberProfile memberProfile = maybeMemberProfile.get();

        Address address = new Address();
        address.setCity(modifyRequest.getCity());
        address.setStreet(modifyRequest.getStreet());
        address.setAddressDetail(modifyRequest.getAddressDetail());
        address.setZipcode(modifyRequest.getZipcode());

        memberProfile.setAddress(address);
        memberProfile.setPhoneNumber(modifyRequest.getPhoneNumber());

        // 멤버 정보 수정
        Member member = maybeMember.get();
        member.setNickName(modifyRequest.getNickName());
        member.setBirthdate(modifyRequest.getBirthdate());


        // 사용자 정보 저장
        memberRepository.save(member);
        memberProfileRepository.save(memberProfile);

        UUID userToken = UUID.randomUUID();

        redisService.deleteByKey(userToken.toString());
        redisService.setKeyAndValue(userToken.toString(), member.getId());

        MyInfoModifyResponse myInfoModifyResponse;
        Optional<CafeCode> op= cafeCodeRepository.findByCodeOfCafe(member.getCafeCode().getCodeOfCafe());

        if(op.isEmpty()) {
            myInfoModifyResponse = new MyInfoModifyResponse(userToken.toString(), member.getId(), member.getNickName(), member.getAuthority().getAuthorityName(), null, member.getCafeCode().getCodeOfCafe(), null, member.getEmail());
        } else {
            myInfoModifyResponse = new MyInfoModifyResponse(userToken.toString(), member.getId(), member.getNickName(), member.getAuthority().getAuthorityName(), op.get().getId(),member.getCafeCode().getCodeOfCafe(), op.get().getCafeName(), member.getEmail());
        }

        return myInfoModifyResponse;
    }

    @Transactional
    @Override
    public void withdrawal(Long memberId) {
        // 등록한 리뷰 삭제
        reviewBoardRepository.deleteByMemberId(memberId);

        // 본인이 작성한 자유게시물 댓글 삭제
        freeBoardCommentRepository.deleteByMemberId(memberId);

        // 등록한 자유게시물 삭제
        freeBoardRepository.deleteByMemberId(memberId);

        // 본인이 작성한 Q&A 댓글 삭제
        commentRepository.deleteByMemberId(memberId);

        // 등록한 Q&A게시물 삭제
        questionBoardRepository.deleteByMemberId(memberId);

        // 주문정보 삭제
        orderInfoRepository.deleteByMemberId(memberId);

        // Recommendation 삭제
        recommendataionRepository.deleteByMemberId(memberId);

        // 찜 카페 삭제
        favoriteRepository.deleteByMemberId(memberId);

        // 회원 정보 삭제
        memberRepository.deleteById(memberId);
    }

    @Transactional
    @Override
    public List<MemberInfoResponse> memberInfoList() {
        List<Member> memberList = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));


        List<MemberInfoResponse> memberInfoResponseList = new ArrayList<>();

        for(Member member: memberList) {
            Optional<Point> maybePoint = pointRepository.findByMemberId(member);

            if(maybePoint.isEmpty()) {
                MemberInfoResponse memberInfoResponse = new MemberInfoResponse(member.getId(), member.getNickName(), member.getAuthority().getAuthorityName().getAUTHORITY_TYPE(),
                        member.getEmail(), member.getMemberProfile().getPhoneNumber(), null);

                memberInfoResponseList.add(memberInfoResponse);
            } else {
                Point point = maybePoint.get();

                MemberInfoResponse memberInfoResponse = new MemberInfoResponse(member.getId(), member.getNickName(), member.getAuthority().getAuthorityName().getAUTHORITY_TYPE(),
                        member.getEmail(), member.getMemberProfile().getPhoneNumber(), point.getPoint());

                memberInfoResponseList.add(memberInfoResponse);
            }
        }

        return memberInfoResponseList;
    }

    @Override
    public List<CafeInfoResponse> cafeInfoList() {
        List<Cafe> cafeList = cafeRepository.findAll();

        List<CafeInfoResponse> cafeInfoResponseList = new ArrayList<>();


        for (Cafe cafe: cafeList) {

            Optional<CafeCode> maybeCafeCode = cafeCodeRepository.findByCodeOfCafe(cafe.getCafeCode().getCodeOfCafe());

            if (maybeCafeCode.isEmpty()) {
                return null;
            }

            CafeCode cafeCode = maybeCafeCode.get();

            // 카페 별점
            List<ReviewBoard> reviewBoardList = reviewBoardRepository.findByCafeName(cafeCode.getCafeName());
            List<Long> ratings = new ArrayList<>();

            for(ReviewBoard reviewBoard: reviewBoardList) {
                Long rating = reviewBoard.getRating();
                ratings.add(rating);
            }

            // 별점 평균
            double ratingAverage = ratings.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);

            // 리뷰 갯수
            int ratingCount = ratings.size();

            // 이번달 주문정보
            List<OrderInfo> monthOrderInfoList = orderInfoRepository.findMonthOrderInfoByCafeId(cafe.getCafeId());

            // 이번달 총 매출
            double monthTotalSales = monthOrderInfoList.stream()
                    .mapToDouble(OrderInfo::getTotalPrice)
                    .sum();

            // 이번달 총 주문
            int monthOrderCount = 0;
            for (OrderInfo orderInfo : monthOrderInfoList) {
                if (orderInfo.isPacking() == true) {
                    monthOrderCount++;
                }
            }

            // 이번달 총 예약
            int monthReservationCount = 0;
            for (OrderInfo orderInfo : monthOrderInfoList) {
                if (orderInfo.isPacking() == false) {
                    monthReservationCount++;
                }
            }

            // 금일 주문정보
            List<OrderInfo> dayOrderInfoList = orderInfoRepository.findDayOrderInfoByCafeId(cafe.getCafeId());

            // 금일 총 매출
            double dayTotalSales = dayOrderInfoList.stream()
                    .mapToDouble(OrderInfo::getTotalPrice)
                    .sum();

            // 금일 총 주문
            int dayOrderCount = dayOrderInfoList.size();

            // 금일 총 예약
            int dayReservationCount = 0;
            for (OrderInfo orderInfo : dayOrderInfoList) {
                if (orderInfo.isPacking() == false) {
                    dayReservationCount++;
                }
            }

            CafeInfoResponse cafeInfoResponse = new CafeInfoResponse(
                    cafe.getCafeId(), cafe.getCafeCode().getCafeName(), cafe.getCafeAddress(), cafe.getCafeTel(),
                    cafe.getStartTime(), cafe.getEndTime(), cafe.getCafeInfo().getSubTitle(), cafe.getCafeInfo().getDescription(),
                    ratingAverage, ratingCount, monthTotalSales, monthOrderCount, dayTotalSales, dayOrderCount, monthReservationCount, dayReservationCount
            );

            cafeInfoResponseList.add(cafeInfoResponse);
        }
        return cafeInfoResponseList;
    }

    @Override
    public CafeInfoResponse myCafeInfo(Long cafeId) {
        Optional<Cafe> maybeCafe = cafeRepository.findById(cafeId);

        if (maybeCafe.isEmpty()) {
            return null;
        }

        Cafe cafe = maybeCafe.get();

        Optional<CafeCode> maybeCafeCode = cafeCodeRepository.findByCodeOfCafe(cafe.getCafeCode().getCodeOfCafe());

        if (maybeCafeCode.isEmpty()) {
            return null;
        }

        CafeCode cafeCode = maybeCafeCode.get();

        // 카페 별점
        List<ReviewBoard> reviewBoardList = reviewBoardRepository.findByCafeName(cafeCode.getCafeName());
        List<Long> ratings = new ArrayList<>();

        for(ReviewBoard reviewBoard: reviewBoardList) {
            Long rating = reviewBoard.getRating();
            ratings.add(rating);
        }

        // 별점 평균
        double ratingAverage = ratings.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);

        // 리뷰 갯수
        int ratingCount = ratings.size();

        // 이번달 주문정보
        List<OrderInfo> monthOrderInfoList = orderInfoRepository.findMonthOrderInfoByCafeId(cafeId);

        // 이번달 총 매출
        double monthTotalSales = monthOrderInfoList.stream()
                .mapToDouble(OrderInfo::getTotalPrice)
                .sum();

        // 이번달 총 주문
        int monthOrderCount = 0;
        for (OrderInfo orderInfo : monthOrderInfoList) {
            if (orderInfo.isPacking() == true) {
                monthOrderCount++;
            }
        }

        // 이번달 총 예약
        int monthReservationCount = 0;
        for (OrderInfo orderInfo : monthOrderInfoList) {
            if (orderInfo.isPacking() == false) {
                monthReservationCount++;
            }
        }

        // 금일 주문정보
        List<OrderInfo> dayOrderInfoList = orderInfoRepository.findDayOrderInfoByCafeId(cafe.getCafeId());

        // 금일 총 매출
        double dayTotalSales = dayOrderInfoList.stream()
                .mapToDouble(OrderInfo::getTotalPrice)
                .sum();

        // 금일 총 주문
        int dayOrderCount = 0;
        for (OrderInfo orderInfo : dayOrderInfoList) {
            if (orderInfo.isPacking() == true) {
                dayOrderCount++;
            }
        }

        // 금일 총 예약
        int dayReservationCount = 0;
        for (OrderInfo orderInfo : dayOrderInfoList) {
            if (orderInfo.isPacking() == false) {
                dayReservationCount++;
            }
        }

        CafeInfoResponse cafeInfoResponse = new CafeInfoResponse(cafe.getCafeId(), cafe.getCafeCode().getCafeName(), cafe.getCafeAddress(),
                cafe.getCafeTel(), cafe.getStartTime(), cafe.getEndTime(), cafe.getCafeInfo().getSubTitle(), cafe.getCafeInfo().getDescription(),
                ratingAverage, ratingCount, monthTotalSales, monthOrderCount, dayTotalSales, dayOrderCount, monthReservationCount, dayReservationCount);


        return cafeInfoResponse;
    }

    @Override
    public void cafeInfoModify(Long cafeId, CafeInfoModifyRequestForm modifyRequest) {
        Optional<Cafe> maybeCafe = cafeRepository.findById(cafeId);

        Cafe cafe = maybeCafe.get();
        cafe.setCafeAddress(modifyRequest.getCafeAddress());
        cafe.setCafeTel(modifyRequest.getCafeTel());
        cafe.setStartTime(modifyRequest.getStartTime());
        cafe.setEndTime(modifyRequest.getEndTime());
        cafe.getCafeInfo().setSubTitle(modifyRequest.getSubTitle());
        cafe.getCafeInfo().setDescription(modifyRequest.getDescription());

        cafeRepository.save(cafe);
    }

    @Override
    public Boolean addPoint(Long memberId, AddPointRequestForm pointRequestForm) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);

        if(maybeMember.isEmpty()) {
            return false;
        }

        Member member = maybeMember.get();
        Optional<Point> maybePoint = pointRepository.findByMemberId(member);

        if(maybePoint.isEmpty()) {
            return false;
        }

        Point point = maybePoint.get();
        point.setPoint(point.getPoint() + pointRequestForm.getPoint());

        pointRepository.save(point);

        PointInfo pointInfo = new PointInfo();

        if(pointRequestForm.getPoint() < 0) {
            pointInfo.setHistory(pointRequestForm.getHistory());
            pointInfo.setAmount(pointRequestForm.getPoint());
            pointInfo.setUse(true);
            pointInfo.setPoint(point);
        } else {
            pointInfo.setHistory(pointRequestForm.getHistory());
            pointInfo.setAmount(pointRequestForm.getPoint());
            pointInfo.setUse(false);
            pointInfo.setPoint(point);
        }

        pointInfoRepository.save(pointInfo);

        return true;
    }

    @Transactional
    @Override
    public List<PointDetailsResponse> pointDetailsList() {
        List<PointInfo> pointInfoList = pointInfoRepository.findAll(Sort.by(Sort.Direction.DESC, "infoId"));

        List<PointDetailsResponse> pointDetailsResponseList = new ArrayList<>();

        for(PointInfo pointInfo: pointInfoList) {
            Optional<Point> maybePoint = pointRepository.findById(pointInfo.getPoint().getPointId());

            if(maybePoint.isEmpty()) {
                return null;
            }

            Point point = maybePoint.get();

            PointDetailsResponse pointDetailsResponse = new PointDetailsResponse(
                    pointInfo.getInfoId(), point.getMember().getNickName(), pointInfo.getHistory(), pointInfo.getDate(),
                    pointInfo.getAmount(), pointInfo.isUse()
            );
            pointDetailsResponseList.add(pointDetailsResponse);
        }

        return pointDetailsResponseList;
    }

    @Override
    public List<PointDetailsResponse> memberPointDetails(Long memberId) {
        Optional<Member> maybeMember = memberRepository.findById(memberId);

        if (maybeMember.isEmpty()) {
            return null;
        }

        Member member = maybeMember.get();

        Optional<Point> maybePoint = pointRepository.findByMemberId(member);

        if (maybeMember.isEmpty()) {
            return null;
        }

        Point point = maybePoint.get();

        List<PointInfo> pointInfoList = pointInfoRepository.findByPointIdOrderByidDesc(point.getPointId());

        List<PointDetailsResponse> pointDetailsResponseList = new ArrayList<>();

        for(PointInfo pointInfo: pointInfoList) {

            PointDetailsResponse pointDetailsResponse = new PointDetailsResponse(
                    pointInfo.getInfoId(), point.getMember().getNickName(), pointInfo.getHistory(), pointInfo.getDate(),
                    pointInfo.getAmount(), pointInfo.isUse()
            );
            pointDetailsResponseList.add(pointDetailsResponse);
        }



        return pointDetailsResponseList;
    }

    @Override
    public Boolean addCafeCode(AddCafeCodeRequestForm cafeCodeRequestForm) {
        CafeCode newCafeCode = new CafeCode();
        newCafeCode.setCafeName(cafeCodeRequestForm.getCafeName());
        newCafeCode.setCodeOfCafe(cafeCodeRequestForm.getCodeOfCafe());

        CafeCode savedCafeCode = cafeCodeRepository.save(newCafeCode);

        return savedCafeCode != null;
    }
}
