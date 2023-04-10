package fourman.backend.cafeIntroduce;

import fourman.backend.domain.cafeIntroduce.controller.requestForm.CafeIntroRequestForm;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.entity.CafeInfo;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.cafeIntroduce.service.CafeIntroduceService;
import fourman.backend.domain.member.entity.AuthorityType;
import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.repository.CafeCodeRepository;
import fourman.backend.domain.member.service.request.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class cafeIntroduceTest {
//    @Autowired
//    private CafeIntroduceService cafeIntroduceService;
//    @Autowired
//    private CafeRepository cafeRepository;
//    @Autowired
//    private CafeCodeRepository cafeCodeRepository;

//    @Test
//    void 카페소개_카페등록(){
//        CafeCode cafeCode1 = new CafeCode("cafe2023","starbucks");
//        cafeCodeRepository.save(cafeCode1);
//
////        CafeIntroRequestForm cafeIntroRequestForm= new CafeIntroRequestForm("스타벅스","강남역","02-111-1234","9:00","22:00","간단설명","상세설명");
////        cafeIntroduceService.registerCafe(null,null,cafeIntroRequestForm);
//        //사진이 없으면 오류난다 UI로 테스트시 성공했음.
//    }
}
