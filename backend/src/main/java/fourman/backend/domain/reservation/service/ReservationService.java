package fourman.backend.domain.reservation.service;

import fourman.backend.domain.reservation.controller.form.ReservationForm;
import fourman.backend.domain.reservation.entity.Seat;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
    List<Seat> getSeats(Long cafeId, Long timeId);

    ResponseEntity<?> makeReservation(ReservationForm reservationForm);
}
