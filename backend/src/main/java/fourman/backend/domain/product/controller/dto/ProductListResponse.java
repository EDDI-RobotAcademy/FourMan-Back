package fourman.backend.domain.product.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductListResponse {

    final private Long productId;
    final private String productName;
    final private Integer price;

}
