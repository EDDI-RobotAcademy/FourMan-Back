package fourman.backend.domain.member.controller;

import fourman.backend.domain.member.controller.form.EmailMatchForm;
import fourman.backend.domain.member.controller.form.EmailPasswordForm;
import fourman.backend.domain.member.controller.form.MemberLoginForm;
import fourman.backend.domain.member.controller.form.MemberRegisterForm;
import fourman.backend.domain.member.service.MemberService;
import fourman.backend.domain.member.service.response.MemberLoginResponse;
import fourman.backend.domain.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    final private MemberService memberService;
    final private RedisService redisService;


    @PostMapping("/check-email/{email}")// 가입시 이메일 중복체크
    public Boolean emailValidation(@PathVariable("email") String email) {
        log.info("emailValidation(): " + email);
        return memberService.emailValidation(email);
    }
    @PostMapping("/check-nickName/{nickName}")//닉네임 중복체크
    public Boolean memberNicknameDuplicateCheck(@PathVariable("nickName") String nickName) {
        log.info("memberNicknameDuplicateCheck()" + nickName);

        return memberService.memberNicknameValidation(nickName);
    }
    @PostMapping("/check-manager/{managerCode}")//회원가입시  관리자코드 인증확인
    public Boolean managerCodeValidation(@PathVariable("managerCode") String managerCode) {
        log.info("managerCodeValidation(): " + managerCode);

        return memberService.managerCodeValidation(managerCode);
    }
    @PostMapping("/check-cafe/{cafeCode}")//회원가입시 카페사업자 코드 인증확인
    public Boolean cafeCodeValidation(@PathVariable("cafeCode") String cafeCode) {
        log.info("cafeCodeValidation(): " + cafeCode);

        return memberService.cafeCodeValidation(cafeCode);
    }

    @PostMapping("/sign-up")//회원가입
    public Boolean signUp(@RequestBody MemberRegisterForm form) {
        log.info("signUp(): " + form);
        log.info("카페사업자 또는 관리자 여부: "+ form.getAuthorityName());
        log.info("코드: "+ form.getCode());
        return memberService.signUp(form.toMemberRegisterRequest());

    }


    @PostMapping("/sign-in")//로그인
    public MemberLoginResponse signIn(@RequestBody MemberLoginForm form) {
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