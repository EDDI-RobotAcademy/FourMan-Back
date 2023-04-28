package fourman.backend.domain.member.controller;

import fourman.backend.domain.member.controller.form.VerificationCodeForm;
import fourman.backend.domain.member.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private final EmailService emailService;


    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestParam("email") String email) {
        return emailService.sendVerificationEmail(email);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody VerificationCodeForm request) {
        return emailService.verifyEmail(request);
    }

}