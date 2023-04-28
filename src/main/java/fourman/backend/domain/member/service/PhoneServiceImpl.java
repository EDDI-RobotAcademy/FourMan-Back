package fourman.backend.domain.member.service;
import fourman.backend.domain.member.controller.form.PhoneVerificationCodeForm;
import fourman.backend.domain.member.controller.form.VerificationCodeForm;
import fourman.backend.domain.member.entity.PhoneVerificationCode;
import fourman.backend.domain.member.entity.VerificationCode;
import fourman.backend.domain.member.repository.PhoneVerificationCodeRepository;
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
public class PhoneServiceImpl implements PhoneService{

    private final PhoneVerificationCodeRepository phoneVerificationCodeRepository;

    @Override
    public ResponseEntity<?> verifyPhone(PhoneVerificationCodeForm request) {
        System.out.println("request.getPhoneNumber()"+ request.getPhoneNumber());
        System.out.println("request.getVerificationCode()"+ request.getVerificationCode());
        String realPhoneNumber=request.getPhoneNumber().replace("-", "");
        System.out.println("realPhoneNumber"+realPhoneNumber);

        // 만료시간이 지난 인증번호 삭제
        LocalDateTime now = LocalDateTime.now();
        phoneVerificationCodeRepository.deleteAllByExpirationBefore(now);

        // 휴대폰으로 인증번호 목록 조회
        List<PhoneVerificationCode> codeList = phoneVerificationCodeRepository.findByPhoneNumber(realPhoneNumber);
        System.out.println("1번");
        // 일치하는 인증번호 검색
        Optional<PhoneVerificationCode> matchedCode = codeList.stream()
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
