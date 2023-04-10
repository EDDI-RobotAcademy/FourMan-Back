package fourman.backend.domain.order.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CartItemRequestForm {

    private final Long productId;
    private final int count;
}
