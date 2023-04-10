package fourman.backend.domain.order.controller;

import fourman.backend.domain.order.controller.form.CartItemRequestForm;
import fourman.backend.domain.order.controller.form.OrderInfoRequestForm;
import fourman.backend.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;

    @PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public void orderRegister(@RequestPart(value = "orderInfo") OrderInfoRequestForm orderInfoRequestForm) {
        log.info("orderRegister()");

        orderService.register(orderInfoRequestForm);
    }
}
