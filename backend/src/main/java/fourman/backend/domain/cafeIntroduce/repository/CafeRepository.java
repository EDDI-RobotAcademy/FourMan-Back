package fourman.backend.domain.cafeIntroduce.repository;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
