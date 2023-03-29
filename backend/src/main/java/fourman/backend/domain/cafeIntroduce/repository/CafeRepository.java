package fourman.backend.domain.cafeIntroduce.repository;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.CafeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findByCafeCode(CafeCode cafeCode);
}
