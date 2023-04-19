package fourman.backend.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.order.entity.OrderInfo;
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

    @JsonBackReference
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ImageResource> imageResourceList = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cafe_id")
    private Cafe cafe;

    public void setImageResource(ImageResource imageResource) {
        imageResourceList.add(imageResource);
        imageResource.setProduct(this);
    }
}
