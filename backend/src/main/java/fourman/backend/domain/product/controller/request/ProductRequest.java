package fourman.backend.domain.product.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ProductRequest {

    final private String productName;
    final private Integer price;
}
