package fourman.backend.domain.member.controller;


import fourman.backend.domain.member.controller.form.PhoneVerificationCodeForm;
import fourman.backend.domain.member.controller.form.VerificationCodeForm;
import fourman.backend.domain.member.entity.PhoneVerificationCode;
import fourman.backend.domain.member.repository.PhoneVerificationCodeRepository;
import fourman.backend.domain.member.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.StorageType;
import net.nurigo.sdk.message.request.MessageListRequest;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MessageListResponse;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/phone")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;

    final DefaultMessageService messageService;
    @Autowired
    private PhoneVerificationCodeRepository phoneVerificationCodeRepository;

    public PhoneController() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("", "", "https://api.coolsms.co.kr");
    }

    /**
     * 단일 메시지 발송 예제
     */
    @PostMapping("/send")
    public SingleMessageSentResponse  sendVerificationCode(@RequestParam("phoneNumber") String phoneNumber) {
        System.out.println("PhoneNumber"+phoneNumber);
        String realPhoneNumber=phoneNumber.replace("-", "");
        System.out.println("realPhoneNumber"+realPhoneNumber);
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01098539993");
        message.setTo(realPhoneNumber);
        String code = String.format("%06d", new Random().nextInt(1000000));
        message.setText("인증코드: "+code);

        PhoneVerificationCode verificationCode = new PhoneVerificationCode(realPhoneNumber, code,LocalDateTime.now().plusMinutes(10));
        phoneVerificationCodeRepository.save(verificationCode);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPhoneNumber(@RequestBody PhoneVerificationCodeForm request) {
        return phoneService.verifyPhone(request);
    }





}