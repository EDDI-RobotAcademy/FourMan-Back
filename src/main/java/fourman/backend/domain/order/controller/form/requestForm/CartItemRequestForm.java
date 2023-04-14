package fourman.backend.domain.order.controller.form.requestForm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CartItemRequestForm {

    private final String productName;
    private final int price;
    private final String drinkType;
    private final int count;
    private final String imageResource;
}
