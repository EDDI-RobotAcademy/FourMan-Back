package fourman.backend.domain.order.service;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.order.controller.form.requestForm.CartItemRequestForm;
import fourman.backend.domain.order.controller.form.requestForm.OrderInfoRequestForm;
import fourman.backend.domain.order.controller.form.responseForm.OrderInfoResponseForm;
import fourman.backend.domain.order.entity.OrderInfo;
import fourman.backend.domain.order.entity.OrderProduct;
import fourman.backend.domain.order.repository.OrderProductRepository;
import fourman.backend.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public void register(OrderInfoRequestForm orderInfoRequestForm) {

        List<OrderProduct> orderProductList = new ArrayList<>();
        List<CartItemRequestForm> cartItemList = orderInfoRequestForm.getCartItemList();
        OrderInfo orderInfo = new OrderInfo();

        // 랜덤 주문번호 생성
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();

        Random random = new Random();
        int orderNumber = random.nextInt(100000);
        String fullOrderNumber = "FourMan" + year + "-" + orderNumber;

        List<String> existOrderNumberList = orderRepository.findFullOrderNumberByOrderNumber();

        // 중복 확인
        while(true) {
            if(!existOrderNumberList.contains(orderNumber)) {
                orderInfo.setOrderNo(fullOrderNumber);
                break;
            }
        }

        orderInfo.setMemberId(orderInfoRequestForm.getMemberId());
        orderInfo.setTotalQuantity(orderInfoRequestForm.getTotalQuantity());
        orderInfo.setTotalPrice(orderInfoRequestForm.getTotalPrice());

        try {
            for (CartItemRequestForm cartItemRequestForm : cartItemList) {
                OrderProduct orderProduct = new OrderProduct(cartItemRequestForm.getProductId(), cartItemRequestForm.getCount());
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
    public List<OrderInfoResponseForm> list(Long memberId) {

        List<OrderInfo> orderInfoList = orderRepository.findOrderInfoByMemberId(memberId);
        List<OrderInfoResponseForm> orderInfoResponseList = new ArrayList<>();

        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        Member member = maybeMember.get();
        String customer = member.getNickName();

        for(OrderInfo orderInfo: orderInfoList) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
            String orderDate = simpleDateFormat.format(orderInfo.getOrderDate());

            List<OrderProduct> orderProductList = orderProductRepository.findOrderProductByOrderId(orderInfo.getOrderId());

            orderInfoResponseList.add(new OrderInfoResponseForm(orderInfo.getOrderId(), orderInfo.getOrderNo(), customer, orderDate,
                                      orderInfo.getTotalQuantity(), orderInfo.getTotalPrice(), orderProductList));
        }

        return orderInfoResponseList;
    }
}
