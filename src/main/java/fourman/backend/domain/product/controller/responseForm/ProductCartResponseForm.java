package fourman.backend.domain.product.controller.responseForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductCartResponseForm {

    final private String productName;
    final private Integer price;
}
