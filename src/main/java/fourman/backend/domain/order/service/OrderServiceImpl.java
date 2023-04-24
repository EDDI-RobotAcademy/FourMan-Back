package fourman.backend.domain.order.service;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.entity.Point;
import fourman.backend.domain.member.repository.CafeCodeRepository;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.member.repository.PointRepository;
import fourman.backend.domain.order.controller.form.requestForm.CartItemRequestForm;
import fourman.backend.domain.order.controller.form.requestForm.OrderInfoRequestForm;
import fourman.backend.domain.order.controller.form.requestForm.OrderReservationRequestForm;
import fourman.backend.domain.order.controller.form.responseForm.OrderInfoResponseForm;
import fourman.backend.domain.order.entity.OrderInfo;
import fourman.backend.domain.order.entity.OrderProduct;
import fourman.backend.domain.order.entity.OrderReservation;
import fourman.backend.domain.order.entity.OrderSeat;
import fourman.backend.domain.order.repository.OrderProductRepository;
import fourman.backend.domain.order.repository.OrderRepository;
import fourman.backend.domain.order.repository.OrderReservationRepository;
import fourman.backend.domain.order.repository.OrderSeatRepository;
import fourman.backend.domain.reservation.entity.Seat;
import fourman.backend.domain.reservation.entity.Time;
import fourman.backend.domain.reservation.repository.SeatRepository;
import fourman.backend.domain.reservation.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Order;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final private OrderRepository orderRepository;
    final private OrderProductRepository orderProductRepository;
    final private MemberRepository memberRepository;
    final private OrderReservationRepository orderReservationRepository;
    final private CafeRepository cafeRepository;
    final private OrderSeatRepository orderSeatRepository;
    final private SeatRepository seatRepository;
    final private TimeRepository timeRepository;
    final private PointRepository pointRepository;
    final private CafeCodeRepository cafeCodeRepository;

    @Override
    public void register(OrderInfoRequestForm orderInfoRequestForm) {

        List<CartItemRequestForm> cartItemList = orderInfoRequestForm.getCartItemList();
        OrderReservationRequestForm reservationInfo = orderInfoRequestForm.getReservationInfo();

        OrderInfo orderInfo = new OrderInfo();
        List<OrderProduct> orderProductList = new ArrayList<>();
        List<OrderSeat> orderSeatList = new ArrayList<>();
        Optional<Cafe> maybeCafe = cafeRepository.findById(orderInfoRequestForm.getCafeId());
        Optional<Member> maybeMember = memberRepository.findByMemberId(orderInfoRequestForm.getMemberId());

        // 포인트 사용 정보 처리
        Member member = maybeMember.get();
        Optional<Point> maybePoint = pointRepository.findByMemberId(member);
        Point point = maybePoint.get();
        Long usePoint = orderInfoRequestForm.getUsePoint();
        Long remainPoint = point.getPoint() - usePoint;
        point.setPoint(remainPoint);
        pointRepository.save(point);

        // 랜덤 주문번호 생성, 주문번호 중복 확인
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();

        while(true) {
            Random random = new Random();
            int orderNumber = random.nextInt(100000);
            String fullOrderNumber = "FourMan" + year + "-" + orderNumber;

            Optional<OrderInfo> existOrder = orderRepository.findExistOrderNumber(fullOrderNumber);

            if(existOrder.isEmpty()) {
                orderInfo.setOrderNo(fullOrderNumber);
                break;
            }
        }

        orderInfo.setTotalQuantity(orderInfoRequestForm.getTotalQuantity());
        orderInfo.setTotalPrice(orderInfoRequestForm.getTotalPrice());
        orderInfo.setUsePoint(orderInfoRequestForm.getUsePoint());
        orderInfo.setPacking(orderInfoRequestForm.isPacking());
        orderInfo.setReady(false);
        orderInfo.setMember(member);
        orderInfo.setCafe(maybeCafe.get());

        // 예약주문일 때(포장 주문 x)
        if( orderInfoRequestForm.isPacking() == false) {
            try {
                for(Integer seatNo :reservationInfo.getSeatList()) {
                    OrderSeat orderSeat = new OrderSeat(seatNo);
                    Optional<Time> maybeTime = timeRepository.findByTime(reservationInfo.getTime());
                    Optional<Seat> existSeat = seatRepository.findSeatNoByCafeAndTimeAndSeatNo(maybeCafe.get(), maybeTime.get(), seatNo);
                    Seat seat = existSeat.get();
                    seat.setReserved(true);
                    seatRepository.save(seat);
                    orderSeatList.add(orderSeat);
                }
            } catch(NullPointerException e) {
                e.printStackTrace();
            }

            OrderReservation orderReservation = new OrderReservation(orderSeatList, reservationInfo.getTime());

            orderInfo.setOrderReservation(orderReservation);

            orderReservationRepository.save(orderReservation);
            orderSeatRepository.saveAll(orderSeatList);
        }

        try {
            for (CartItemRequestForm cartItemRequestForm : cartItemList) {
                OrderProduct orderProduct = new OrderProduct(
                        cartItemRequestForm.getProductName(),
                        cartItemRequestForm.getPrice(),
                        cartItemRequestForm.getDrinkType(),
                        cartItemRequestForm.getCount(),
                        cartItemRequestForm.getImageResource());
                orderProductList.add(orderProduct);
                orderInfo.setOrderProduct(orderProduct);
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        }

        orderRepository.save(orderInfo);
        orderProductRepository.saveAll(orderProductList);

    }

    @Override
    public List<OrderInfoResponseForm> orderList(Long memberId) {

        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        Member member = maybeMember.get();
        List<OrderInfo> orderInfoList = orderRepository.findOrderInfoByMember(member);
        List<OrderInfoResponseForm> orderInfoResponseList = new ArrayList<>();

        String customer = member.getNickName();
        for(OrderInfo orderInfo: orderInfoList) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
            String orderDate = simpleDateFormat.format(orderInfo.getOrderDate());
            List<OrderProduct> orderProductList = orderProductRepository.findOrderProductByOrderId(orderInfo.getOrderId());

            if(orderInfo.getOrderReservation() == null) { // 포장 주문
                System.out.println("orderReservation값: null -> 포장 주문");

                Optional<CafeCode> maybeCafeCode = cafeCodeRepository.findById(orderInfo.getCafe().getCafeId());

                CafeCode cafeCode = maybeCafeCode.get();
                System.out.println("cafeCode: " + cafeCode.getCafeName());

                orderInfoResponseList.add(new OrderInfoResponseForm(orderInfo.getOrderId(), orderInfo.getOrderNo(), customer, orderDate,
                        orderInfo.getTotalQuantity(), orderInfo.getTotalPrice(), orderInfo.getUsePoint(), orderInfo.isPacking(), orderInfo.isReady(),
                        cafeCode.getCafeName(), null, null, orderProductList));
            } else { // 예약 주문
                System.out.println("orderReservation값 존재 -> 예약 주문");
                Optional<OrderReservation> maybeOrderReservation = orderReservationRepository.findByReservationId(orderInfo.getOrderReservation().getId());
                if(maybeOrderReservation.isEmpty()) {
                    System.out.println("maybeOrderReservation값이 존재하지 않습니다.");
                }
                OrderReservation orderReservation = maybeOrderReservation.get();

                Optional<CafeCode> maybeCafeCode = cafeCodeRepository.findById(orderInfo.getCafe().getCafeId());

                CafeCode cafeCode = maybeCafeCode.get();

                orderInfoResponseList.add(new OrderInfoResponseForm(orderInfo.getOrderId(), orderInfo.getOrderNo(), customer, orderDate,
                                          orderInfo.getTotalQuantity(), orderInfo.getTotalPrice(), orderInfo.getUsePoint(), orderInfo.isPacking(), orderInfo.isReady(),
                                          cafeCode.getCafeName(), orderReservation.getTime(), orderReservation.getSeatNoList(), orderProductList));
            }
        }

        return orderInfoResponseList;
    }

    @Override
    public Number getHoldPoint(Long memberId) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        Member member = maybeMember.get();
        Long point = member.getPoint().getPoint();

        return point;
    }
}
