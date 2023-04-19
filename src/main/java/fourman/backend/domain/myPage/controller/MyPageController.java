package fourman.backend.domain.myPage.controller;

import fourman.backend.domain.myPage.controller.requestForm.CafeInfoModifyRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.MyInfoModifyRequestForm;
import fourman.backend.domain.myPage.service.MyPageService;
import fourman.backend.domain.myPage.service.responseForm.CafeInfoResponseForm;
import fourman.backend.domain.myPage.service.responseForm.MemberInfoResponseForm;
import fourman.backend.domain.myPage.service.responseForm.MyInfoModifyResponseForm;
import fourman.backend.domain.myPage.service.responseForm.MyInfoResponseForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/my-page")
@RequiredArgsConstructor
public class MyPageController {

    final private MyPageService myPageService;

    @GetMapping("/{memberId}")
    public MyInfoResponseForm myInfo(
            @PathVariable("memberId") Long memberId) {

        log.info("memberInfo(): " + memberId);

        return myPageService.myInfo(memberId);
    }

    @PutMapping("/member-info-modify/{memberId}")
    public MyInfoModifyResponseForm memberInfoModify(@PathVariable("memberId") Long memberId,
                                                     @RequestBody MyInfoModifyRequestForm modifyRequest) {

        return myPageService.myInfoModify(memberId, modifyRequest);
    }

    @DeleteMapping("/withdrawal/{memberId}")
    public void withdrawal(@PathVariable("memberId") Long memberId) {
        log.info("withdrawal()");

        myPageService.withdrawal(memberId);
    }
    @GetMapping("/member-list")
    public List<MemberInfoResponseForm> memberInfoList() {
        return myPageService.memberInfoList();
    }

    @GetMapping("/cafe-list")
    public List<CafeInfoResponseForm> cafeInfoList() {
        return myPageService.cafeInfoList();
    }

    @GetMapping("/my-cafe-info/{cafeId}")
    public CafeInfoResponseForm myCafeInfo(
            @PathVariable("cafeId") Long cafeId) {

        log.info("myCafeInfo(): " + cafeId);

        return myPageService.myCafeInfo(cafeId);
    }

    @PutMapping("/cafe-info-modify/{cafeId}")
    public void cafeInfoModify(@PathVariable("cafeId") Long cafeId,
                                                     @RequestBody CafeInfoModifyRequestForm modifyRequest) {

        myPageService.cafeInfoModify(cafeId, modifyRequest);
    }

}
