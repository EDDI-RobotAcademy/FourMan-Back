package fourman.backend.domain.member.repository;

import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.entity.ManagerCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CafeCodeRepository extends JpaRepository<CafeCode, Long> {



    @Query("select cc from CafeCode cc where cc.cafeName = :cafeName")
    Optional<CafeCode> findCafeIdByCafeName(String cafeName);
    @Query("select mc from CafeCode mc where mc.codeOfCafe = :codeOfCafe")
    Optional<CafeCode> findByCodeOfCafe(String codeOfCafe);
}
