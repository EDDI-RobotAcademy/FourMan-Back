package fourman.backend.domain.order.service;

import fourman.backend.domain.order.controller.form.requestForm.OrderInfoRequestForm;
import fourman.backend.domain.order.controller.form.responseForm.OrderInfoResponseForm;

import java.util.List;

public interface OrderService {

    void register(OrderInfoRequestForm orderInfoRequestForm);

    List<OrderInfoResponseForm> list(Long memberId);
}
