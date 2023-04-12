package fourman.backend.domain.reservation.service;

import fourman.backend.domain.reservation.controller.form.ReservationForm;
import fourman.backend.domain.reservation.entity.Seat;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    ResponseEntity<Map<String, Object>> getSeats(Long cafeId, String time);

    ResponseEntity<?> makeReservation(ReservationForm reservationForm);

    void resetAllSeats();
}
