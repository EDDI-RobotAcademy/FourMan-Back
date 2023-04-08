package fourman.backend.domain.reservation.repository;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.reservation.entity.CafeTable;
import fourman.backend.domain.reservation.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeTableRepository extends JpaRepository<CafeTable, Long> {


    List<CafeTable> findByCafe(Cafe cafe);
}
