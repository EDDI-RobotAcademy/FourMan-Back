package fourman.backend.domain.member.controller.form;

import lombok.Getter;

@Getter
public class VerificationCodeForm {
    private String email;
    private String verificationCode;
}
