package fourman.backend.domain.reservation.entity;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
    @NonNull
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @NonNull
    private List<Seat> seats;
    @Column(nullable = false)
    private LocalDateTime reservationTime;

}
