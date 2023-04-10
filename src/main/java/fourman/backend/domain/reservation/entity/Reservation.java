package fourman.backend.domain.reservation.entity;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @JoinColumn(name = "member_id")
    private Member member;

//    @OneToMany
//    private List<Time> times;//제거예정

    @OneToMany
    private List<Seat> seats;
    @Column(nullable = false)
    private LocalDateTime reservationTime;

}
