package fourman.backend.domain.member.repository;

import fourman.backend.domain.member.entity.PointInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointInfoRepository extends JpaRepository<PointInfo, Long> {
}
