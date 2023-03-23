package fourman.backend.member;

import fourman.backend.domain.member.entity.AuthorityType;
import fourman.backend.domain.member.entity.ManagerCode;
import fourman.backend.domain.member.repository.ManagerCodeRepository;
import fourman.backend.domain.member.service.MemberService;
import fourman.backend.domain.member.service.request.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private  ManagerCodeRepository managerCodeRepository;

    @Test
    void managerCodeTest() {
        ManagerCode managerCode1 = new ManagerCode("cafe2022");
        ManagerCode managerCode2 = new ManagerCode("cafe2023");
        managerCodeRepository.save(managerCode1);
        managerCodeRepository.save(managerCode2);
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
}
