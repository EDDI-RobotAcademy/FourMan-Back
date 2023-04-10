package fourman.backend.domain.member.repository;

import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.entity.ManagerCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CafeCodeRepository extends JpaRepository<CafeCode, Long> {

    @Query("select mc from CafeCode mc where mc.code = :cafeCode")
    Optional<CafeCode> findByCode(String cafeCode);

}
