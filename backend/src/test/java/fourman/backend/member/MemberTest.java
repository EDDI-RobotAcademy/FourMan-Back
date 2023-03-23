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
                "meme@me.com", "meme", "김미미", 19931106, AuthorityType.MEMBER, false,
                "서울특별시","중랑구","면목동","어딘가","010-0000-0000");
        //when
        memberService.signUp(registerRequest);
        //then
        assertThat(memberService.emailValidation("meme@me.com")).isEqualTo(false);


    }
    @Test
    void memberSignUpTest() {
        MemberRegisterRequest registerRequest = new MemberRegisterRequest(
                "meme@me.com", "meme", "김미미", 19931106, AuthorityType.MEMBER, false,
                "서울특별시","중랑구","면목동","어딘가","010-0000-0000");
        memberService.signUp(registerRequest);
    }

    @Test
    void managerSignUpTest() {
        MemberRegisterRequest registerRequest = new MemberRegisterRequest(
                "manager@manager.com", "manager", "박관리", 19931106, AuthorityType.MANAGER,true,
                "수원특별시","종로구","명동","어딘가","010-0000-0000");
        memberService.signUp(registerRequest);
    }

    @Test
    void memberSignInTest() {
        MemberRegisterRequest registerRequest = new MemberRegisterRequest(
                "meme@me.com", "meme", "김영진", 19931106, AuthorityType.MEMBER, false,
                "서울특별시","중랑구","면목동","어딘가","010-0000-0000");
        memberService.signUp(registerRequest);
        MemberLoginRequest loginRequest = new MemberLoginRequest("meme@me.com", "meme");
        memberService.signIn(loginRequest);
    }
}
