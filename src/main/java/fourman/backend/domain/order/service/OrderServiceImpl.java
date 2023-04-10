package fourman.backend.domain.order.service;

import fourman.backend.domain.order.controller.form.CartItemRequestForm;
import fourman.backend.domain.order.controller.form.OrderInfoRequestForm;
import fourman.backend.domain.order.entity.OrderInfo;
import fourman.backend.domain.order.entity.OrderProduct;
import fourman.backend.domain.order.repository.OrderProductRepository;
import fourman.backend.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final private OrderRepository orderRepository;
    final private OrderProductRepository orderProductRepository;

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

        orderInfo.setCustomer(orderInfoRequestForm.getCustomer());
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
}
