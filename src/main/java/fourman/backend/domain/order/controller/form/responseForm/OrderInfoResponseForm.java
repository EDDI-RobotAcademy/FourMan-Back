package fourman.backend.domain.order.controller.form.responseForm;

import fourman.backend.domain.order.entity.OrderProduct;
import fourman.backend.domain.order.entity.OrderSeat;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class OrderInfoResponseForm {

    final private Long orderId;
    final private String orderNo;
    final private String customer;
    final private String orderDate;
    final private int totalQuantity;
    final int totalPrice;
    final Long usePoint;
    final Long savedPoint;
    final boolean isPacking;
    final boolean isReady;
    final String canceledAt;
    final String cafeName;
    final String cafeThumbnailFile;
    final String reservationTime;
    final List<OrderSeat> seatNoList;
    final List<OrderProduct> orderProductList;

    public OrderInfoResponseForm(Long orderId, String orderNo, String customer, String orderDate, int totalQuantity,
                                 int totalPrice, Long usePoint, Long savedPoint, boolean isPacking, boolean isReady, String canceledAt,
                                 String cafeName, String cafeThumbnailFile ,String reservationTime,
                                 List<OrderSeat> seatNoList, List<OrderProduct> orderProductList) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.usePoint = usePoint;
        this.savedPoint = savedPoint;
        this.isPacking = isPacking;
        this.isReady = isReady;
        this.canceledAt = canceledAt;
        this.cafeName = cafeName;
        this.cafeThumbnailFile = cafeThumbnailFile;
        this.reservationTime = reservationTime;
        this.seatNoList = seatNoList;
        this.orderProductList = orderProductList;
    }
}
