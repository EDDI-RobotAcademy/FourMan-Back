package fourman.backend.domain.member.service;

import fourman.backend.domain.member.controller.form.PhoneVerificationCodeForm;
import fourman.backend.domain.member.controller.form.VerificationCodeForm;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PhoneService {

    ResponseEntity<?> verifyPhone(PhoneVerificationCodeForm request);
}
