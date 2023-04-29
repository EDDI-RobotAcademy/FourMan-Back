package fourman.backend.domain.reservation.service;

import fourman.backend.domain.reservation.entity.CafeTable;
import fourman.backend.domain.reservation.entity.Seat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class CafeLayout {
    private List<Seat> seats;
    private List<CafeTable> cafeTables;

    public CafeLayout(List<Seat> seats, List<CafeTable> cafeTables) {
        this.seats = seats;
        this.cafeTables = cafeTables;
    }

    // getter, setter 생략
}
