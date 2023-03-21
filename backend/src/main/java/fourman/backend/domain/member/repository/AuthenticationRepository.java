package fourman.backend.domain.member.repository;

import fourman.backend.domain.member.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

}