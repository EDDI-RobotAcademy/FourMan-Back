package fourman.backend.domain.myPage.myInfo.controller;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.myPage.myInfo.service.MyInfoService;
import fourman.backend.domain.myPage.myInfo.service.responseForm.MyInfoResponseForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
