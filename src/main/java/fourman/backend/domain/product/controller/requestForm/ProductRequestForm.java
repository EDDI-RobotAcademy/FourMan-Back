package fourman.backend.domain.product.controller.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ProductRequestForm {

    final private String productName;
    final private Integer price;
    final private String drinkType;
    final private Long cafeId;
}
