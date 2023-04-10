package fourman.backend.domain.reservation.service.response;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.reservation.entity.Time;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SeatResponse {
    private final int seatNo;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Cafe cafe;
    private final Time time;
    private final boolean isReserved;


}
