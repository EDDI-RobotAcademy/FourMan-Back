package fourman.backend.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.Member;
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
    @CreationTimestamp
    private Date orderDate;
    @Column(nullable = false)
    private int totalQuantity;
    @Column(nullable = false)
    private int totalPrice;
    @Column(nullable = false)
    private Long usePoint;
    @Column(nullable = false)
    private Long savedPoint;
    @Column(nullable = false)
    private boolean isPacking;
    @Column(nullable = false)
    private boolean isReady;
    @Column
    private Date canceledAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private OrderReservation orderReservation;
    @JsonManagedReference
    @OneToMany(mappedBy = "orderInfo",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    public void setOrderProduct(OrderProduct orderProduct) {
        orderProductList.add(orderProduct);
        orderProduct.setOrderInfo(this);
    }

    public void setOrderReservation(OrderReservation orderReservation) {
        this.orderReservation = orderReservation;
        orderReservation.setOrderInfo(this);
    }
}
