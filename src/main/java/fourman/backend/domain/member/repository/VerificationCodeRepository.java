package fourman.backend.domain.member.repository;
import fourman.backend.domain.member.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    List<VerificationCode> findByEmail(String email);

    void deleteAllByExpirationBefore(LocalDateTime now);
}