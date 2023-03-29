package fourman.backend.domain.product.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String drinkType;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ImageResource> imageResourceList = new ArrayList<>();

    public void setImageResource(ImageResource imageResource) {
        imageResourceList.add(imageResource);
        imageResource.setProduct(this);
    }
}
