package fourman.backend.domain.member.controller;

import fourman.backend.domain.member.controller.form.EmailMatchForm;
import fourman.backend.domain.member.controller.form.EmailPasswordForm;
import fourman.backend.domain.member.controller.form.MemberLoginForm;
import fourman.backend.domain.member.controller.form.MemberRegisterForm;
import fourman.backend.domain.member.service.MemberService;
import fourman.backend.domain.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8887", allowedHeaders = "*")
public class MemberController {

    final private MemberService memberService;
    final private RedisService redisService;


    @PostMapping("/check-email/{email}")//이메일체크
    public Boolean emailValidation(@PathVariable("email") String email) {
        log.info("emailValidation(): " + email);

        return memberService.emailValidation(email);
    }
    @PostMapping("/sign-up")//회원가입
    public Boolean signUp(@RequestBody MemberRegisterForm form) {
        log.info("signUp(): " + form);

        return memberService.signUp(form.toMemberRegisterRequest());
    }

    @PostMapping("/sign-in")//로그인
    public String signIn(@RequestBody MemberLoginForm form) {
        log.info("signIn(): " + form);

        return memberService.signIn(form.toMemberLoginRequest());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody String token) {
        token = token.substring(0, token.length() - 1);//=이 붙어서 제거해줌.
        log.info("logout(): " + token);

        redisService.deleteByKey(token);
    }
    @PostMapping("/emailMatch")// PW찾기시 이메일있는지확인
    public Boolean emailMatchPhone(@Validated @RequestBody EmailMatchForm form, BindingResult bindingResult) {
        log.info("MainFormController#emailMatchPhone: {}", form);
        if (bindingResult.hasFieldErrors()) {
            return false;
        }
        return memberService.emailMatch(form.toEmailMatchRequest());
    }
    @PostMapping("/applyNewPassword")// PW찾기시 새로운 비밀번호적용
    public Boolean applyNewPassword(@Validated @RequestBody EmailPasswordForm form) {
        log.info("MainFormController#applyNewPassword: {}", form);

        return memberService.applyNewPassword(form.toEmailPasswordRequest());
    }




}