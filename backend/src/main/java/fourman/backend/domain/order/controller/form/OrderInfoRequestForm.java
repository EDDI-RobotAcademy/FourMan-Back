package fourman.backend.domain.order.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class OrderInfoRequestForm {

    private final String customer;
    private final int totalQuantity;
    private final int totalPrice;
    private final List<CartItemRequestForm> cartItemList;

}
