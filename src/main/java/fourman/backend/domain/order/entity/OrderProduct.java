package fourman.backend.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class OrderProduct {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String drinkType;

    @Column(nullable = false)
    private int count;

    @Column
    private String imageResource;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderInfo orderInfo;

    public OrderProduct(String productName, int price, String drinkType, int count, String imageResource) {
        this.productName = productName;
        this.price = price;
        this.drinkType = drinkType;
        this.count = count;
        this.imageResource = imageResource;
    }
}