package fourman.backend.domain.reservation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "reservation")
    @NonNull
    private List<Seat> seats;

    @JsonIgnore//OnetoOne에서는 순환참조 일어나기떄문에 방지.
    @OneToOne
    @JoinColumn(name = "time_id")
    private Time time;  //예약타임
    @Column(nullable = false  , columnDefinition = "TIMESTAMP")
    private LocalDateTime reservationTime;//예약결제시행 시간

}
