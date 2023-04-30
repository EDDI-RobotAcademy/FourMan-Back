package fourman.backend.domain.order.controller;

import fourman.backend.domain.aop.aspect.SecurityAnnotations;
import fourman.backend.domain.order.controller.form.requestForm.OrderCancelRequestForm;
import fourman.backend.domain.order.controller.form.requestForm.OrderInfoRequestForm;
import fourman.backend.domain.order.controller.form.requestForm.OrderReservationRequestForm;
import fourman.backend.domain.order.controller.form.responseForm.CafeOrderInfoResponseForm;
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

    private final OrderService orderService;

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public void orderRegister(@RequestPart(value = "orderInfo") OrderInfoRequestForm orderInfoRequestForm) {
        log.info("orderRegister()");

        orderService.register(orderInfoRequestForm);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @GetMapping("/orderList/{memberId}")
    public List<OrderInfoResponseForm> orderList(@PathVariable("memberId") Long memberId) {
        log.info("orderList()");

        return orderService.orderList(memberId);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @GetMapping("/point/{memberId}")
    public Long getHoldPoint(@PathVariable("memberId") Long memberId) {
        log.info("getHoldPoint()");

        return orderService.getHoldPoint(memberId);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @PostMapping("/cancel/{orderId}")
    public void orderCancel(@PathVariable("orderId") Long orderId,
                            @RequestBody OrderCancelRequestForm orderCancelRequestForm) {
        log.info("orderCancel()");

        orderService.orderCancel(orderId, orderCancelRequestForm);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @GetMapping("/cafeOrderList/{cafeId}")
    public List<CafeOrderInfoResponseForm> cafeOrderList(@PathVariable("cafeId") Long cafeId) {
        log.info("cafeOrderList()");

        return orderService.cafeOrderList(cafeId);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @PostMapping("/ready/{orderId}")
    public void orderReady(@PathVariable("orderId") Long orderId) {
        log.info("orderReady()");

        orderService.orderReady(orderId);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @PostMapping("/isReservationAvailable/{cafeId}")
    public boolean isReservationAvailable(@PathVariable("cafeId") Long cafeId, @RequestPart(value = "reservationInfo") OrderReservationRequestForm orderReservationRequestForm) {
        log.info("isReservationAvailable()");

        return orderService.isAvailable(cafeId, orderReservationRequestForm);
    }
}
