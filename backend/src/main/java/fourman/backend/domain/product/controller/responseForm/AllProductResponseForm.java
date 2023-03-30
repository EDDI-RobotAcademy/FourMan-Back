package fourman.backend.domain.product.controller.responseForm;

import fourman.backend.domain.product.entity.ImageResource;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class AllProductResponseForm {

    final private Long productId;
    final private String productName;
    final private String drinkType;
    final private Integer price;
    final private Integer count;
    final private Integer totalPrice;
    final private List<ImageResource> imageResourceList;

    public AllProductResponseForm(Long productId, String productName,
                                  String drinkType, Integer price, Integer count, Integer totalPrice, List<ImageResource> imageResourceList) {
        this.productId = productId;
        this.productName = productName;
        this.drinkType = drinkType;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
        this.imageResourceList = imageResourceList;
    }

}
