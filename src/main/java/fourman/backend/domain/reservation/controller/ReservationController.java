package fourman.backend.domain.reservation.controller;

import fourman.backend.domain.aop.aspect.SecurityAnnotations;
import fourman.backend.domain.reservation.controller.form.ReservationForm;
import fourman.backend.domain.reservation.entity.Seat;
import fourman.backend.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    final private ReservationService reservationService;


    @GetMapping("/cafe/{cafeId}/time/{time}")
    public  ResponseEntity<Map<String, Object>> getSeats(@PathVariable Long cafeId, @PathVariable String time) {
        return reservationService.getSeats(cafeId,time);

    }
    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @PostMapping("/register")
    public ResponseEntity<?> makeReservation(@RequestBody ReservationForm reservationForm) {
        return reservationService.makeReservation(reservationForm);
    }

    @DeleteMapping("/seats/delete")
    public ResponseEntity<?> resetAllSeats() {
        reservationService.resetAllSeats();
        return ResponseEntity.ok("좌석 초기화 성공");
    }


}