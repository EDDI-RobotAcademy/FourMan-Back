package fourman.backend.domain.reservation.controller.form;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.reservation.entity.Seat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReservationForm {
    private Cafe cafe ;
    private Long memberId;
    private List<Seat> seatList;

}
