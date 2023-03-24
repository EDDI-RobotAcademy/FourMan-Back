package fourman.backend.member;

import fourman.backend.domain.member.entity.AuthorityType;
import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.entity.ManagerCode;
import fourman.backend.domain.member.repository.CafeCodeRepository;
import fourman.backend.domain.member.repository.ManagerCodeRepository;
import fourman.backend.domain.member.service.MemberService;
import fourman.backend.domain.member.service.request.MemberLoginRequest;
import fourman.backend.domain.member.service.request.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private  ManagerCodeRepository managerCodeRepository;
    @Autowired
    private CafeCodeRepository cafeCodeRepository;

    @Test
    void managerCodeTest() {
        ManagerCode managerCode1 = new ManagerCode("manager2022");
        ManagerCode managerCode2 = new ManagerCode("manager2023");
        managerCodeRepository.save(managerCode1);
        managerCodeRepository.save(managerCode2);
    }
    @Test
    void cafeCodeTest() {
        CafeCode cafeCode1 = new CafeCode("cafe2022");
        CafeCode cafeCode2 = new CafeCode("cafe2023");
        cafeCodeRepository.save(cafeCode1);
        cafeCodeRepository.save(cafeCode2);
    }
    @Test
    void 이메일_중복_확인체크(){
        //given
        MemberRegisterRequest registerRequest = new MemberRegisterRequest(
                "meme@me.com", "meme", "김미미", 19931106, AuthorityType.MEMBER,null,
                "서울특별시","중랑구","면목동","어딘가","010-0000-0000");
        //when
        memberService.signUp(registerRequest);
        //then
        assertThat(memberService.emailValidation("meme@me.com")).isEqualTo(false);
    }
    @Test
    void 닉네임_중복_확인체크(){
        //given
        MemberRegisterRequest registerRequest = new MemberRegisterRequest(
                "meme@me.com", "meme", "김영진", 19931106, AuthorityType.MEMBER, null,
                "서울특별시","중랑구","면목동","어딘가","010-0000-0000");
        //when
        memberService.signUp(registerRequest);
        //then
        assertThat(memberService.memberNicknameValidation("김영진")).isEqualTo(false);
    }
    @Test
    void 매니저_회원가입_체크(){
        //given
        ManagerCode managerCode1 = new ManagerCode("manager1");
        MemberRegisterRequest registerRequest = new MemberRegisterRequest(
                "manager1@manager.com", "manager1", "김영진", 19931106, AuthorityType.MANAGER,"manager1",
                "서울특별시","중랑구","면목동","어딘가","010-0000-0000");
        //when
        memberService.signUp(registerRequest);
        //then
    }
    @Test
    void 카페사업자_회원가입_체크(){
        //given
        CafeCode cafeCode1 = new CafeCode("cafe1");
        MemberRegisterRequest registerRequest = new MemberRegisterRequest(
                "cafe1@cafe.com", "cafe1", "김영진", 19931106, AuthorityType.CAFE,"cafe1",
                "서울특별시","중랑구","면목동","어딘가","010-0000-0000");
        //when
        memberService.signUp(registerRequest);
        //then
    }
    @Test
    void memberSignUpTest() {
        MemberRegisterRequest registerRequest = new MemberRegisterRequest(
                "meme@me.com", "meme", "김미미", 19931106, AuthorityType.MEMBER, null,
                "서울특별시","중랑구","면목동","어딘가","010-0000-0000");
        memberService.signUp(registerRequest);
    }



    @Test
    void memberSignInTest() {
        MemberRegisterRequest registerRequest = new MemberRegisterRequest(
                "meme@me.com", "meme", "김미미", 19931106, AuthorityType.MEMBER, null,
                "서울특별시","중랑구","면목동","어딘가","010-0000-0000");
        memberService.signUp(registerRequest);
        MemberLoginRequest loginRequest = new MemberLoginRequest("meme@me.com","meme");
        memberService.signIn(loginRequest);
    }
}
