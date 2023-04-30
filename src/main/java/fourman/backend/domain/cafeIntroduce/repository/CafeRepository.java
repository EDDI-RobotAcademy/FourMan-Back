package fourman.backend.domain.cafeIntroduce.repository;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroListResponse;
import fourman.backend.domain.member.entity.CafeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findByCafeCode(CafeCode cafeCode);

    @Query("SELECT f.cafe FROM Favorite f WHERE f.member.id = :memberId ORDER BY f.cafe.cafeId DESC")
    List<Cafe> findCafesByMemberIdOrderByCafeIdDesc(long memberId);
    @Query("SELECT c FROM Cafe c, Favorite f WHERE c.cafeId = f.cafe.cafeId GROUP BY c ORDER BY COUNT(f) DESC")
    List<Cafe> findAllByOrderByFavoritesDesc();
}
