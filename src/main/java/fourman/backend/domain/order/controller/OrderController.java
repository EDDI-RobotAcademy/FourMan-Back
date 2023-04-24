package fourman.backend.domain.order.controller;

import fourman.backend.domain.order.controller.form.requestForm.OrderInfoRequestForm;
import fourman.backend.domain.order.controller.form.responseForm.OrderInfoResponseForm;
import fourman.backend.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/orderList/{memberId}")
    public List<OrderInfoResponseForm> orderList(@PathVariable("memberId") Long memberId) {
        log.info("orderList()");

        return orderService.orderList(memberId);
    }

    @GetMapping("/point/{memberId}")
    public Number getHoldPoint(@PathVariable("memberId") Long memberId) {
        log.info("getHoldPoint()");

        return orderService.getHoldPoint(memberId);
    }
}
