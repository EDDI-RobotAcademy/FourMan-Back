package fourman.backend.domain.product.controller.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class EditProductRequestForm {

    final private Long productId;
    final private String productName;
    final private Integer price;
    final private String drinkType;
    final private String editedProductImageName;

}
