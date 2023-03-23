package fourman.backend.domain.product.controller.responseForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductListResponseForm {

    final private Long productId;
    final private String productName;
    final private Integer price;

}
