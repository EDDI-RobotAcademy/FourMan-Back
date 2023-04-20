package fourman.backend.domain.member.repository;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("select p from Point p join fetch p.member where p.member = :member")
    Optional<Point> findByMemberId(Member member);
}
