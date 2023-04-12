package fourman.backend.domain.myPage.myInfo.controller;

import fourman.backend.domain.member.service.response.MemberLoginResponse;
import fourman.backend.domain.myPage.myInfo.controller.requestForm.MemberInfoModifyRequestForm;
import fourman.backend.domain.myPage.myInfo.service.MyInfoService;
import fourman.backend.domain.myPage.myInfo.service.responseForm.MyInfoResponseForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/my-info")
@RequiredArgsConstructor
public class MyInfoController {

    final private MyInfoService myInfoService;

    @GetMapping("/{memberId}")
    public MyInfoResponseForm myInfo(
            @PathVariable("memberId") Long memberId) {

        log.info("memberInfo(): " + memberId);

        return myInfoService.myInfo(memberId);
    }

    @PutMapping("/member-info-modify/{memberId}")
    public MemberLoginResponse memberInfoModify(@PathVariable("memberId") Long memberId,
                                                @RequestBody MemberInfoModifyRequestForm modifyRequest) {

        return myInfoService.memberInfoModify(memberId, modifyRequest);
    }
}
