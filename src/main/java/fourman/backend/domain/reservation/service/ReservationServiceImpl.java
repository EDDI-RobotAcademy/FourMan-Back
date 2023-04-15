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
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new NoSuchElementException("Cafe not found with ID: " + cafeId));;
        Optional<Time> optionalTime = timeRepository.findByTime(timeString);
        System.out.println("cafe:" + cafe);
        System.out.println("optionalTime:" + optionalTime);

        Time time = optionalTime.orElseGet(() -> {
            Time newTime = new Time(timeString);
            timeRepository.save(newTime);
            return newTime;
        });

//        Time time=null;
//        if(optionalTime.isPresent()){//특정시간이 데베에 저장이 되어있다면
//            time= optionalTime.get();
//        }else{//데이터베이스에 없는 시간이면
//            System.out.println("optionalTime: " + optionalTime);
//            time = new Time(timeString);//시간을 만들어서
//            System.out.println("time: " + time);
//            timeRepository.save(time);//데베에 저장
//        }

        List<Seat> seatList =seatRepository.findByCafeAndTime(cafe, time);
        if (seatList.isEmpty()) {
            List<Seat> seatList2 = seatRepository.findByCafeAndTime(cafe, null);
            List<Seat> newSeats = new ArrayList<>();
            for (Seat originalSeat : seatList2) {
                Seat copiedSeat = new Seat();
                copiedSeat.setCafe(originalSeat.getCafe());
                copiedSeat.setHeight(originalSeat.getHeight());
                copiedSeat.setSeatNo(originalSeat.getSeatNo());
                copiedSeat.setTime(time);
                copiedSeat.setWidth(originalSeat.getWidth());
                copiedSeat.setX(originalSeat.getX());
                copiedSeat.setY(originalSeat.getY());
                copiedSeat.setReserved(originalSeat.isReserved());
                seatRepository.save(copiedSeat);
                newSeats.add(copiedSeat);
            }
            seatList = newSeats;
        }
//        if (seatList.size()==0){// seat레포지토리에 time이 존재하지않는경우
//            List<Seat> seatList2 =seatRepository.findByCafeAndTime(cafe, null);
//            for (Seat originalSeat : seatList2) {
//                // 원본 Seat 객체를 복사합니다.
//                Seat copiedSeat = new Seat();
//                copiedSeat.setCafe(originalSeat.getCafe());
//                copiedSeat.setHeight(originalSeat.getHeight());
//                copiedSeat.setSeatNo(originalSeat.getSeatNo());
//                copiedSeat.setTime(time);
//                copiedSeat.setWidth(originalSeat.getWidth());
//                copiedSeat.setX(originalSeat.getX());
//                copiedSeat.setY(originalSeat.getY());
//                copiedSeat.setReserved(originalSeat.isReserved());
//                // 변경된 값이 있는 새 Seat 객체를 저장합니다.
//                seatRepository.save(copiedSeat);
//                seatList=seatList2;
//            }
//        }
        List<CafeTable> tableList =cafeTableRepository.findByCafe(cafe);
        System.out.println("seatList.toString() : "+seatList.toString());

        Map<String, Object> response = new HashMap<>();
        response.put("seatResponse", seatList);
        response.put("tableResponse", tableList);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> makeReservation(ReservationForm reservationForm) {

        Reservation reservationRequest = new Reservation();
        System.out.println("시간:"+LocalDateTime.now());
        reservationRequest.setReservationTime(LocalDateTime.now());

        reservationRepository.save(reservationRequest);//FK키가 먼저 들어가있어야함

        System.out.println("memberId:"+ reservationForm.getMemberId());
        Member member = memberRepository.findById(reservationForm.getMemberId()).orElseThrow(() -> new NoSuchElementException("Member not found with ID: " + reservationForm.getMemberId()));;
        System.out.println("member:"+ member);
        reservationRequest.setMember(member);

        reservationRequest.setCafe(reservationForm.getCafe());
        System.out.println("cafe:"+ reservationForm.getCafe());

        Time Time1 = timeRepository.findByTime(reservationForm.getTimeString())
                .orElseThrow(() -> new NoSuchElementException("Time not found: " + reservationForm.getTimeString()));;
        reservationRequest.setTime(Time1);

            List<Seat> seatList3 =new ArrayList<>();
            for(Seat seat :reservationForm.getSeatList()){ // 각 자리마다
                    if (!seat.isReserved()) {//예약이 안되어있으면
                        seat.setReserved(true);//예약하라
                        seat.setReservation(reservationRequest);
                        System.out.println("예약완료");
                        seatList3.add(seat);
                        seatRepository.save(seat);
                   }
            }
            reservationRequest.setSeats(seatList3);
//            seatRepository.saveAll(seatList3);//이걸로 바꾸면 에러가난다.

        System.out.println("예약저장완료");
        reservationRepository.save(reservationRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public void resetAllSeats() {

        List<Seat> allSeats = seatRepository.findAll();
        for (Seat seat : allSeats) {
            seat.setReserved(false);
        }
        seatRepository.saveAll(allSeats);


    }
}
