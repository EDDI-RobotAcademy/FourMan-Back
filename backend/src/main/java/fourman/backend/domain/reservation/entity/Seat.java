package fourman.backend.domain.reservation.entity;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Seat {
    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int seatNo;
    private int x;
    private int y;
    private int width;
    private int height;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time time;

    @Column
    private boolean isReserved = false;


}
