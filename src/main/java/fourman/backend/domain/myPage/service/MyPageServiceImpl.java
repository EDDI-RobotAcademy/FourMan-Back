package fourman.backend.domain.myPage.service;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.entity.FreeBoardComment;
import fourman.backend.domain.freeBoard.repository.FreeBoardCommentRepository;
import fourman.backend.domain.freeBoard.repository.FreeBoardRepository;
import fourman.backend.domain.member.entity.*;
import fourman.backend.domain.member.repository.*;
import fourman.backend.domain.myPage.controller.requestForm.AddPointRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.CafeInfoModifyRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.MyInfoModifyRequestForm;
import fourman.backend.domain.myPage.service.responseForm.*;
import fourman.backend.domain.order.entity.OrderInfo;
import fourman.backend.domain.order.repository.OrderRepository;
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
    final private ReservationRepository reservationRepository;
    final private FreeBoardCommentRepository freeBoardCommentRepository;
    final private RedisService redisService;
    final private CafeRepository cafeRepository;
    final private PointRepository pointRepository;
    final private CafeCodeRepository cafeCodeRepository;
    final private OrderRepository orderRepository;
    final private PointInfoRepository pointInfoRepository;

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
        Optional<CafeCode> op= cafeCodeRepository.findByCode(member.getCode());

        if(op.isEmpty()) {
            myInfoModifyResponse = new MyInfoModifyResponse(userToken.toString(), member.getId(), member.getNickName(), member.getAuthority().getAuthorityName(), null, member.getCode(), null, member.getEmail());
        } else {
            myInfoModifyResponse = new MyInfoModifyResponse(userToken.toString(), member.getId(), member.getNickName(), member.getAuthority().getAuthorityName(), op.get().getId(), member.getCode(), op.get().getCafeName(), member.getEmail());
        }

        return myInfoModifyResponse;
    }

    @Override
    public void withdrawal(Long memberId) {
        // 등록한 리뷰 삭제
        List<ReviewBoard> reviewBoardList = reviewBoardRepository.findReviewBoardByMemberId(memberId);
        for(ReviewBoard reviewBoard: reviewBoardList) {
            reviewBoardRepository.delete(reviewBoard);
        }

        // 본인이 작성한 자유게시물 댓글 삭제
        List<FreeBoardComment> freeBoardCommentList = freeBoardCommentRepository.findFreeBoardCommentByMemberId(memberId);
        for(FreeBoardComment freeBoardComment: freeBoardCommentList) {
            freeBoardCommentRepository.delete(freeBoardComment);
        }

        // 등록한 자유게시물 삭제
        List<FreeBoard> freeBoardList = freeBoardRepository.findFreeBoardByMemberId(memberId);

        for(FreeBoard freeBoard: freeBoardList) {
            List<FreeBoardComment> freeBoardCommentList2 = freeBoardCommentRepository.findFreeBoardCommentByBoardId(freeBoard.getBoardId());
            for (FreeBoardComment freeBoardComment: freeBoardCommentList2) {
                freeBoardCommentRepository.delete(freeBoardComment);
            }
            freeBoardRepository.delete(freeBoard);
        }

        // 본인이 작성한 Q&A 댓글 삭제
        List<Comment> commentList = commentRepository.findCommentByMemberId(memberId);
        for(Comment comment: commentList) {
            commentRepository.delete(comment);
        }

        // 등록한 Q&A게시물 삭제
        List<QuestionBoard> questionBoardList = questionBoardRepository.findMyQuestionBoardByMemberId(memberId);

        for(QuestionBoard questionBoard: questionBoardList) {
            List<Comment> commentList2 = commentRepository.findCommentByBoardId(questionBoard.getBoardId());
            for(Comment comment: commentList2) {
                commentRepository.delete(comment);
            }
            questionBoardRepository.delete(questionBoard);
        }

        //예약정보 삭제시 자리도 같이 삭제되는 오류
//        // 예약정보 삭제
//        List<Reservation> reservationList = reservationRepository.findReservationByMemberId(memberId);
//        for(Reservation reservation: reservationList) {
//            reservationRepository.delete(reservation);
//        }

        // 회원 정보 삭제
        memberRepository.deleteById(memberId);

    }

    @Transactional
    @Override
    public List<MemberInfoResponse> memberInfoList() {
        List<Member> memberList = memberRepository.findAll();


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

            Optional<CafeCode> maybeCafeCode = cafeCodeRepository.findByCode(cafe.getCafeCode().getCode());

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
            List<OrderInfo> monthOrderInfoList = orderRepository.findMonthOrderInfoByCafeId(cafe.getCafeId());

            // 이번달 총 매출
            double monthTotalSales = monthOrderInfoList.stream()
                    .mapToDouble(OrderInfo::getTotalPrice)
                    .sum();

            // 이번달 총 주문
            int monthOrderCount = monthOrderInfoList.size();

            // 이번달 총 예약
            int monthReservationCount = 0;
            for (OrderInfo orderInfo : monthOrderInfoList) {
                if (orderInfo.isPacking() == false) {
                    monthReservationCount++;
                }
            }

            // 금일 주문정보
            List<OrderInfo> dayOrderInfoList = orderRepository.findDayOrderInfoByCafeId(cafe.getCafeId());

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

        Optional<CafeCode> maybeCafeCode = cafeCodeRepository.findByCode(cafe.getCafeCode().getCode());

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
        List<OrderInfo> monthOrderInfoList = orderRepository.findMonthOrderInfoByCafeId(cafeId);

        // 이번달 총 매출
        double monthTotalSales = monthOrderInfoList.stream()
                .mapToDouble(OrderInfo::getTotalPrice)
                .sum();

        // 이번달 총 주문
        int monthOrderCount = monthOrderInfoList.size();

        // 이번달 총 예약
        int monthReservationCount = 0;
        for (OrderInfo orderInfo : monthOrderInfoList) {
            if (orderInfo.isPacking() == false) {
                monthReservationCount++;
            }
        }

        // 금일 주문정보
        List<OrderInfo> dayOrderInfoList = orderRepository.findDayOrderInfoByCafeId(cafe.getCafeId());

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
}
