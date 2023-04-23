package fourman.backend.domain.order.controller.form.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class OrderInfoRequestForm {

    private final Long memberId;
    private final int totalQuantity;
    private final int totalPrice;
    private final Long usePoint;
    private final boolean isPacking;
    private final Long cafeId;
    private final List<CartItemRequestForm> cartItemList;
    private final OrderReservationRequestForm reservationInfo;

}
