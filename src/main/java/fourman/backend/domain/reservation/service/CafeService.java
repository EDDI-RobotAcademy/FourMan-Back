package fourman.backend.domain.reservation.service;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.reservation.entity.CafeTable;
import fourman.backend.domain.reservation.entity.Seat;
import fourman.backend.domain.reservation.repository.CafeTableRepository;
import fourman.backend.domain.reservation.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CafeService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private CafeTableRepository cafeTableRepository;
    private  List<CafeLayout> predefinedLayouts;

    public CafeService() {
        predefinedLayouts = new ArrayList<>();

        // 미리 정의된 레이아웃 1
        List<Seat> seatsLayout1 = Arrays.asList(
                new Seat(1, 10, 100, 50, 50, null, null, false),
                new Seat(2, 110, 100, 50, 50, null, null, false),
                new Seat(3, 210, 100, 50, 50, null, null, false),
                new Seat(4, 310, 100, 50, 50, null, null, false),
                new Seat(5, 10, 250, 50, 50, null, null, false),
                new Seat(6, 110, 250, 50, 50, null, null, false),
                new Seat(7, 10, 400, 50, 50, null, null, false),
                new Seat(8, 110, 400, 50, 50, null, null, false),
                new Seat(9, 10, 500, 50, 50, null, null, false),
                new Seat(10, 110, 500, 50, 50, null, null, false),
                new Seat(11, 10, 650, 50, 50, null, null, false),
                new Seat(12, 110, 650, 50, 50, null, null, false),
                new Seat(13, 350, 200, 50, 50, null, null, false),
                new Seat(14, 470, 200, 50, 50, null, null, false),
                new Seat(15, 350, 300, 50, 50, null, null, false),
                new Seat(16, 470, 300, 50, 50, null, null, false),
                new Seat(17, 350, 400, 50, 50, null, null, false),
                new Seat(18, 470, 400, 50, 50, null, null, false)
        );

        List<CafeTable> cafeTablesLayout1 = Arrays.asList(
                new CafeTable("TABLE", 0, 30, 400, 70, null),
                new CafeTable("TABLE", 10, 310, 160, 70, null),
                new CafeTable("TABLE", 10, 570, 160, 70, null),
                new CafeTable("TABLE", 410, 190, 50, 70, null),
                new CafeTable("TABLE", 410, 290, 50, 70, null),
                new CafeTable("TABLE", 410, 390, 50, 70, null),
                new CafeTable("COUNTER", 350, 500, 170, 200, null)
        );

        predefinedLayouts.add(new CafeLayout(seatsLayout1, cafeTablesLayout1));

        // 미리 정의된 레이아웃 2
        // ... 레이아웃 2에 대한 정의
    }

    public void insertDataForCafe(Cafe cafe, String layoutIndex) {

        CafeLayout layout = predefinedLayouts.get(Integer.parseInt(layoutIndex)-1);
//        Optional<Cafe> cafeOptional = cafeRepository.findById((long) cafeId);

//        if (cafeOptional.isPresent()) {
//            Cafe cafe = cafeOptional.get();

            List<Seat> seats = layout.getSeats().stream()
                    .map(s -> new Seat(s.getSeatNo(), s.getX(), s.getY(), s.getWidth(), s.getHeight(), cafe, s.getTime() != null ? s.getTime() : null, s.isReserved()))
                    .collect(Collectors.toList());

            List<CafeTable> cafeTables = layout.getCafeTables().stream()
                    .map(t -> new CafeTable(t.getTableName(), t.getX(), t.getY(), t.getWidth(), t.getHeight(), cafe))
                    .collect(Collectors.toList());

            seatRepository.saveAll(seats);
            cafeTableRepository.saveAll(cafeTables);
//        } else {
            // 적절한 에러 처리
//        }
    }
}