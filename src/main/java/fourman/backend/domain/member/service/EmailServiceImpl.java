package fourman.backend.domain.member.service;
import fourman.backend.domain.member.controller.form.VerificationCodeForm;
import fourman.backend.domain.member.entity.VerificationCode;
import fourman.backend.domain.member.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;
    private final VerificationCodeRepository verificationCodeRepository;


    public ResponseEntity<Map<String, Object>> sendVerificationEmail(String to) {
        System.out.println("to"+to);
        String decodedEmail = URLDecoder.decode(to, StandardCharsets.UTF_8);
        System.out.println("decodedEmail"+decodedEmail);
//        String realEmail= decodedEmail.substring(0, decodedEmail.length() - 1);//=이 붙어서 제거해줌.
        String realEmail=decodedEmail;
        log.info("realEmail " +realEmail);
        String verificationCode = generateVerificationCode();

        VerificationCode code = new VerificationCode();
        code.setEmail(realEmail);
        code.setCode(verificationCode);
        code.setExpiration(LocalDateTime.now().plusMinutes(10)); // 10분 후 만료
        System.out.println("3번");
        verificationCodeRepository.save(code);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        System.out.println("5번");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(realEmail);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드: " + verificationCode);
        sendEmailAsync(message);
        System.out.println("6번");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @Async
    public void sendEmailAsync(SimpleMailMessage message) {
        javaMailSender.send(message);
    }
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // 100000 ~ 999999
        return String.valueOf(code);
    }
    public ResponseEntity<?> verifyEmail(VerificationCodeForm request) {
        System.out.println("request.getEmail()"+ request.getEmail());
        System.out.println("request.getVerificationCode()"+ request.getVerificationCode());

        // 만료시간이 지난 인증번호 삭제
        LocalDateTime now = LocalDateTime.now();
        verificationCodeRepository.deleteAllByExpirationBefore(now);

        // 이메일로 인증번호 목록 조회
        List<VerificationCode> codeList = verificationCodeRepository.findByEmail(request.getEmail());
        System.out.println("1번");
        // 일치하는 인증번호 검색
        Optional<VerificationCode> matchedCode = codeList.stream()
                .filter(code -> code.getCode().equals(request.getVerificationCode()))
                .findFirst();
        System.out.println("2번");
        if (matchedCode.isPresent()) {
            System.out.println("인증 성공");
            return ResponseEntity.ok("인증에 성공하였습니다.");
        } else {
            System.out.println("인증번호 불일치");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
        }
    }





}
