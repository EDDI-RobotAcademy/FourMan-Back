package fourman.backend.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class OrderInfo {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(nullable = false)
    private String orderNo;
    @Column(nullable = false)
    private Long memberId;
    @CreationTimestamp
    private Date orderDate;
    @Column(nullable = false)
    private int totalQuantity;
    @Column(nullable = false)
    private int totalPrice;

    @JsonManagedReference
    @OneToMany(mappedBy = "orderInfo",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    public void setOrderProduct(OrderProduct orderProduct) {
        orderProductList.add(orderProduct);
        orderProduct.setOrderInfo(this);
    }
}
