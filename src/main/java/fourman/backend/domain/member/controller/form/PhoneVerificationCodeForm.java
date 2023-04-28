package fourman.backend.domain.member.controller.form;

import lombok.Getter;

@Getter
public class PhoneVerificationCodeForm {
    private String phoneNumber;
    private String verificationCode;
}
