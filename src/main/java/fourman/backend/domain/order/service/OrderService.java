package fourman.backend.domain.order.service;

import fourman.backend.domain.order.controller.form.CartItemRequestForm;
import fourman.backend.domain.order.controller.form.OrderInfoRequestForm;

public interface OrderService {
    void register(OrderInfoRequestForm orderInfoRequestForm);
}
