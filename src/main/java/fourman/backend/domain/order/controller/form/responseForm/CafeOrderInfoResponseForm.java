package fourman.backend.domain.order.controller.form.responseForm;

import fourman.backend.domain.order.entity.OrderProduct;
import fourman.backend.domain.order.entity.OrderSeat;
import lombok.Getter;

import java.util.List;

@Getter
public class CafeOrderInfoResponseForm {

    final private Long orderId;
    final private String orderNo;
    final private String customer;
    final private String phoneNumber;
    final private String orderDate;
    final private int totalQuantity;
    final int totalPrice;
    final Long usePoint;
    final boolean isPacking;
    final boolean isReady;
    final String canceledAt;
    final String cafeName;
    final String reservationTime;
    final List<OrderSeat> seatNoList;
    final List<OrderProduct> orderProductList;

    public CafeOrderInfoResponseForm(Long orderId, String orderNo, String customer, String phoneNumber, String orderDate, int totalQuantity,
                                 int totalPrice, Long usePoint, boolean isPacking, boolean isReady, String canceledAt,
                                 String cafeName, String reservationTime, List<OrderSeat> seatNoList, List<OrderProduct> orderProductList) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.customer = customer;
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.usePoint = usePoint;
        this.isPacking = isPacking;
        this.isReady = isReady;
        this.canceledAt = canceledAt;
        this.cafeName = cafeName;
        this.reservationTime = reservationTime;
        this.seatNoList = seatNoList;
        this.orderProductList = orderProductList;
    }
}
