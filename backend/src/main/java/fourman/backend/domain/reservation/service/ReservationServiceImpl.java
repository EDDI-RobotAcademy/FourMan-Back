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

import java.time.LocalDateTime;
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
        Reservation reservationRequest = new Reservation();

        Optional<Cafe> cafe = cafeRepository.findById(reservationForm.getCafeId());
        Optional<Member> member = memberRepository.findById(reservationForm.getMemberId());
        reservationRequest.setCafe(cafe.get());
        reservationRequest.setMember(member.get());

        List<Time> timeList =new ArrayList<>();
        for(String timeString :reservationForm.getTimeStringList()) {//선택한 특정 시간대 마다
            Time time=null;
            Optional<Time> optionalTime = timeRepository.findByTime(timeString);
            if(optionalTime.isPresent()){//특정시간이 데베에 저장이 되어있다면
                time= optionalTime.get();
                timeList.add(time);
            }else{//데이터베이스에 없는 시간이면
                time = new Time(timeString);
                timeList.add(time);
                timeRepository.save(time);//데베에 저장
            }


            List<Seat> seatList =new ArrayList<>();
            for(int seatNo :reservationForm.getSeatNoList()){ //특정시간대의 각 자리마다.
                Optional <Seat> optionalSeat =seatRepository.findBySeatNoAndCafeAndTime(seatNo, cafe.get(), time);
                if (optionalSeat.isPresent()) {//seat이 존재한다면(미리 seat의 배치정보는 입력해놔야한다.
                    Seat seat = optionalSeat.get();
                    if (!seat.isReserved()) {//예약이 안되어있으면
                        seat.setReserved(true);//예약하라
                        seatList.add(seat);
                        seatRepository.save(seat);
                    }
                }
            }
            reservationRequest.setSeats(seatList);
        }
        reservationRequest.setTimes(timeList);
        reservationRequest.setReservationTime(LocalDateTime.now());
        reservationRepository.save(reservationRequest);

        return ResponseEntity.ok().build();
    }
}
