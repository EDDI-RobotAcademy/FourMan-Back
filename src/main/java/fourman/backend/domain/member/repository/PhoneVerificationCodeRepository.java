package fourman.backend.domain.member.repository;
import fourman.backend.domain.member.entity.PhoneVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PhoneVerificationCodeRepository extends JpaRepository<PhoneVerificationCode, Long> {


    void deleteAllByExpirationBefore(LocalDateTime now);

    List<PhoneVerificationCode> findByPhoneNumber(String phoneNumber);
}