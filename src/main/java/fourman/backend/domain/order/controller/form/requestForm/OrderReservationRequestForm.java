package fourman.backend.domain.order.controller.form.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class OrderReservationRequestForm {

    private final List<Integer> seatList;
    private final String time;
}
