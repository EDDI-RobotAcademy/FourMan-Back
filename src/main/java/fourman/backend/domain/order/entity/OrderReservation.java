package fourman.backend.domain.order.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class OrderReservation {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @OneToMany(mappedBy = "orderReservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderSeat> seatNoList;

    @Column(nullable = false)
    private String time;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private OrderInfo orderInfo;

    public OrderReservation(List<OrderSeat> seatNoList, String time) {
        this.seatNoList = seatNoList;
        this.time = time;
        for (OrderSeat seat : seatNoList) {
            seat.setOrderReservation(this);
        }
    }
}
