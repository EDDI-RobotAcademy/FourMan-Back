package fourman.backend.domain.reservation.service.request;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.reservation.entity.Seat;
import fourman.backend.domain.reservation.entity.Time;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private Long reservationId;
    private Cafe cafe;
    private Member member;
    private List<Seat> seats;
    private LocalDateTime reservationTime;
}
