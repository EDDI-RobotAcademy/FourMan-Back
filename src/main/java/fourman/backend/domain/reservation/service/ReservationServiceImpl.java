package fourman.backend.domain.reservation.service;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.reservation.controller.form.ReservationForm;
import fourman.backend.domain.reservation.entity.CafeTable;
import fourman.backend.domain.reservation.entity.Reservation;
import fourman.backend.domain.reservation.entity.Seat;
import fourman.backend.domain.reservation.entity.Time;
import fourman.backend.domain.reservation.repository.CafeTableRepository;
import fourman.backend.domain.reservation.repository.ReservationRepository;
import fourman.backend.domain.reservation.repository.SeatRepository;
import fourman.backend.domain.reservation.repository.TimeRepository;
import fourman.backend.domain.reservation.service.request.ReservationRequest;
import fourman.backend.domain.reservation.service.response.SeatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final CafeRepository cafeRepository;
    private final TimeRepository timeRepository;
    private final MemberRepository memberRepository;
    private final SeatRepository seatRepository;
    private final CafeTableRepository cafeTableRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getSeats(Long cafeId, String timeString) {
        Optional<Cafe> cafe = cafeRepository.findById(cafeId);
        Optional<Time> optionalTime = timeRepository.findByTime(timeString);
        System.out.println("cafe:" + cafe.get());
        System.out.println("optionalTime:" + optionalTime);

        Time time=null;
        if(optionalTime.isPresent()){//특정시간이 데베에 저장이 되어있다면
            time= optionalTime.get();
        }else{//데이터베이스에 없는 시간이면
            System.out.println("optionalTime: " + optionalTime);
            time = new Time(timeString);//시간을 만들어서
            System.out.println("time: " + time);
            timeRepository.save(time);//데베에 저장

            Optional<Time> optionalTime2 = timeRepository.findByTime(timeString);
            System.out.println("optionalTime2.get() : "+ optionalTime2.get() );
        }

        List<Seat> seatList =seatRepository.findByCafeAndTime(cafe.get(), time);
        if (seatList.size()==0){// seat레포지토리에 time이 존재하지않는경우
            List<Seat> seatList2 =seatRepository.findByCafeAndTime(cafe.get(), null);
            for (Seat originalSeat : seatList2) {
                // 원본 Seat 객체를 복사합니다.
                Seat copiedSeat = new Seat();
                copiedSeat.setCafe(originalSeat.getCafe());
                copiedSeat.setHeight(originalSeat.getHeight());
                copiedSeat.setSeatNo(originalSeat.getSeatNo());
                copiedSeat.setTime(time);
                copiedSeat.setWidth(originalSeat.getWidth());
                copiedSeat.setX(originalSeat.getX());
                copiedSeat.setY(originalSeat.getY());
                copiedSeat.setReserved(originalSeat.isReserved());
                // 변경된 값이 있는 새 Seat 객체를 저장합니다.
                seatRepository.save(copiedSeat);
                seatList=seatList2;
            }
        }

        List<CafeTable> tableList =cafeTableRepository.findByCafe(cafe.get());
        System.out.println("seatList.toString() : "+seatList.toString());

        Map<String, Object> response = new HashMap<>();
        response.put("seatResponse", seatList);
        response.put("tableResponse", tableList);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> makeReservation(ReservationForm reservationForm) {
        System.out.println("memberId:"+ reservationForm.getMemberId());
        Reservation reservationRequest = new Reservation();
        Optional<Member> member = memberRepository.findById(reservationForm.getMemberId());
        System.out.println("member:"+ member.get());
        reservationRequest.setCafe(reservationForm.getCafe());
        System.out.println("cafe:"+ reservationForm.getCafe());
        reservationRequest.setMember(member.get());

            List<Seat> seatList =new ArrayList<>();
            for(Seat seat :reservationForm.getSeatList()){ // 각 자리마다
                    if (!seat.isReserved()) {//예약이 안되어있으면
                        seat.setReserved(true);//예약하라
                        System.out.println("예약완료");
                        seatList.add(seat);
                        seatRepository.save(seat);
                    }
            }
            reservationRequest.setSeats(seatList);
        System.out.println("시간:"+LocalDateTime.now());
        reservationRequest.setReservationTime(LocalDateTime.now());
        reservationRepository.save(reservationRequest);
        System.out.println("예약저장완료");

//        Optional<Reservation> reservationOpt = reservationRepository.findById(1L);
//
//        if (reservationOpt.isPresent()) {
//            Reservation reservation = reservationOpt.get();
//            List<Seat> seats = reservation.getSeats();
//           for (Seat seat : seats){
//               System.out.println(seat);
//           }
//        }

        return ResponseEntity.ok().build();
    }
}
