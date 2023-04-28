package fourman.backend.domain.member.service;

import fourman.backend.domain.member.controller.form.VerificationCodeForm;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmailService {
    ResponseEntity<Map<String, Object>> sendVerificationEmail(String email);

    ResponseEntity<?> verifyEmail(VerificationCodeForm request);
}
