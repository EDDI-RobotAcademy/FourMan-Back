package fourman.backend.domain.reservation.service;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.reservation.controller.form.ReservationForm;
import fourman.backend.domain.reservation.entity.Reservation;
import fourman.backend.domain.reservation.entity.Seat;
import fourman.backend.domain.reservation.entity.Time;
import fourman.backend.domain.reservation.repository.ReservationRepository;
import fourman.backend.domain.reservation.repository.SeatRepository;
import fourman.backend.domain.reservation.repository.TimeRepository;
import fourman.backend.domain.reservation.service.request.ReservationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final CafeRepository cafeRepository;
    private final TimeRepository timeRepository;
    private final MemberRepository memberRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<Seat> getSeats(Long cafeId, Long timeId) {
        Optional<Cafe> cafe = cafeRepository.findById(cafeId);
        Optional<Time> time = timeRepository.findById(timeId);
        return seatRepository.findByCafeAndTime(cafe.get(), time.get());
    }

    @Override
    public ResponseEntity<?> makeReservation(ReservationForm reservationForm) {
        Optional<Cafe> cafe = cafeRepository.findById(reservationForm.getCafeId());
        Optional<Time> time = timeRepository.findById(reservationForm.getTimeId());
        Optional<Member> member = memberRepository.findById(reservationForm.getMemberId());
//        List<Seat> seats = seatRepository.findByCafeAndTime(cafe.get(), time.get());

        List<Seat> seatList =new ArrayList<>();
        for(Long seatId :reservationForm.getSeatIdList()){
            Optional <Seat> optionalSeat =seatRepository.findByIdAndCafeAndTime(seatId, cafe.get(), time.get());
            if (optionalSeat.isPresent()) {
                Seat seat = optionalSeat.get();
                if (!seat.isReserved()) {//예약이 안되어있으면
                    seat.setReserved(true);//예약하라
                    seatList.add(seat);
                    seatRepository.save(seat);
                }
            }

        }

//        long count = seats.stream().filter(Seat::isReserved).count();
//        int numReservedSeats= (int)count;

//        if (numReservedSeats + request.getNumSeats() > cafe.getMaxSeats()) {
//            throw new SeatUnavailableException();
//        }

//        seats.stream().filter(Seat::isReserved).forEach(seat -> {
//            seat.setReserved(true);
//            seatRepository.save(seat);
//        });

        Reservation reservationRequest = new Reservation();
        reservationRequest.setCafe(cafe.get());
        reservationRequest.setTime(time.get());
        reservationRequest.setMember(member.get());
        reservationRequest.setSeats(seatList);
        reservationRepository.save(reservationRequest);

        return ResponseEntity.ok().build();
    }
}
