package fourman.backend.domain.reservation.controller.form;

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
    private Long cafeId ;
    private Long memberId;

    private List<String> timeStringList;
    private List<Integer> seatNoList;

}
