package fourman.backend.domain.order.controller.form.responseForm;

import fourman.backend.domain.order.entity.OrderProduct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    final List<OrderProduct> orderProductList;
    public OrderInfoResponseForm(Long orderId, String orderNo, String customer, String orderDate, int totalQuantity,
                                 int totalPrice, List<OrderProduct> orderProductList) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.orderProductList = orderProductList;
    }
}
