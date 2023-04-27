package fourman.backend.domain.member.repository;

import fourman.backend.domain.member.entity.ManagerCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ManagerCodeRepository extends JpaRepository<ManagerCode, Long> {

    @Query("select mc from ManagerCode mc where mc.codeOfManager = :codeOfManager")
    Optional<ManagerCode> findByCodeOfManager(String codeOfManager);
}
