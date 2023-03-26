package fourman.backend.cafeIntroduce;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.entity.CafeInfo;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.cafeIntroduce.service.CafeIntroduceService;
import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.repository.CafeCodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class cafeIntroduceTest {
    @Autowired
    private CafeIntroduceService cafeIntroduceService;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private CafeCodeRepository cafeCodeRepository;

    @Test
    void 카페소개_카페등록(){
        CafeCode cafeCode1 = new CafeCode("cafe2022","starbucks");
        cafeCodeRepository.save(cafeCode1);
        Cafe cafe= new Cafe("스타벅스","강남역","02-111-1234","9:00","22:00",null,cafeCode1);

    }
}
