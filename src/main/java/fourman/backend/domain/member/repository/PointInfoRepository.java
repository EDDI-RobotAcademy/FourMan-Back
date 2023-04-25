package fourman.backend.domain.member.repository;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.entity.Point;
import fourman.backend.domain.member.entity.PointInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PointInfoRepository extends JpaRepository<PointInfo, Long> {

    @Query("select pi from PointInfo pi join fetch pi.point p where p.pointId = :pointId order by pi.infoId desc")
    List<PointInfo> findByPointIdOrderByidDesc(Long pointId);
}
