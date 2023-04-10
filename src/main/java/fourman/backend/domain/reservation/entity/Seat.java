package fourman.backend.domain.reservation.entity;

import com.sun.istack.NotNull;
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
    @NotNull
    private int seatNo;
    @NotNull
    private int x;
    @NotNull
    private int y;
    @NotNull
    private int width;
    @NotNull
    private int height;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time time;

    @NotNull
    @Column(columnDefinition = "BIT(1) DEFAULT 0")
    private boolean isReserved=false;


}
