package fourman.backend.member;

import fourman.backend.domain.member.entity.ManagerCode;
import fourman.backend.domain.member.repository.ManagerCodeRepository;
import fourman.backend.domain.member.service.MemberService;
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
}
