package fourman.backend.domain.order.service;

import fourman.backend.domain.order.controller.form.requestForm.OrderCancelRequestForm;
import fourman.backend.domain.order.controller.form.requestForm.OrderInfoRequestForm;
import fourman.backend.domain.order.controller.form.responseForm.CafeOrderInfoResponseForm;
import fourman.backend.domain.order.controller.form.responseForm.OrderInfoResponseForm;

import java.util.List;

public interface OrderService {

    void register(OrderInfoRequestForm orderInfoRequestForm);

    List<OrderInfoResponseForm> orderList(Long memberId);

    Long getHoldPoint(Long memberId);

    void orderCancel(Long orderId, OrderCancelRequestForm orderCancelRequestForm);

    List<CafeOrderInfoResponseForm> cafeOrderList(Long cafeId);

    void orderReady(Long orderId);
}
