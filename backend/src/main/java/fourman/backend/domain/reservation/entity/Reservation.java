package fourman.backend.domain.reservation.entity;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Reservation {
    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time time;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    private List<Seat> seats;

}
