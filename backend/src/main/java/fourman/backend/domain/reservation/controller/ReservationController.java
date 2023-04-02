package fourman.backend.domain.reservation.controller;

import fourman.backend.domain.reservation.controller.form.ReservationForm;
import fourman.backend.domain.reservation.entity.Seat;
import fourman.backend.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    final private ReservationService reservationService;


    @GetMapping("/cafe/{cafeId}/time/{timeId}/seats")
    public List<Seat> getSeats(@PathVariable Long cafeId, @PathVariable Long timeId) {
        return reservationService.getSeats(cafeId,timeId);

    }

    @PostMapping("/register")
    public ResponseEntity<?> makeReservation(@RequestBody ReservationForm reservationForm) {
        return reservationService.makeReservation(reservationForm);
    }


}