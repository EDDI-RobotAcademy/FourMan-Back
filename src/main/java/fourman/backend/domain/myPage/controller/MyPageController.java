package fourman.backend.domain.myPage.controller;

import fourman.backend.domain.aop.aspect.SecurityAnnotations;
import fourman.backend.domain.myPage.controller.requestForm.AddPointRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.CafeInfoModifyRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.MyInfoModifyRequestForm;
import fourman.backend.domain.myPage.service.MyPageService;
import fourman.backend.domain.myPage.service.responseForm.*;
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

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @GetMapping("/{memberId}")
    public MyInfoResponse myInfo(
            @PathVariable("memberId") Long memberId) {

        log.info("memberInfo(): " + memberId);

        return myPageService.myInfo(memberId);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @GetMapping("/side-bar/{memberId}")
    public MyInfoSideBarResponse myInfoSideBar(@PathVariable("memberId") Long memberId) {
        log.info("myInfoSideBar()");

        return myPageService.myInfoSideBar(memberId);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @PutMapping("/member-info-modify/{memberId}")
    public MyInfoModifyResponse memberInfoModify(@PathVariable("memberId") Long memberId,
                                                 @RequestBody MyInfoModifyRequestForm modifyRequest) {

        return myPageService.myInfoModify(memberId, modifyRequest);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @DeleteMapping("/withdrawal/{memberId}")
    public void withdrawal(@PathVariable("memberId") Long memberId) {
        log.info("withdrawal()");

        myPageService.withdrawal(memberId);
    }
    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.MANAGER)
    @GetMapping("/member-list")
    public List<MemberInfoResponse> memberInfoList() {
        return myPageService.memberInfoList();
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.MANAGER)
    @GetMapping("/cafe-list")
    public List<CafeInfoResponse> cafeInfoList() {
        return myPageService.cafeInfoList();
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @GetMapping("/my-cafe-info/{cafeId}")
    public CafeInfoResponse myCafeInfo(
            @PathVariable("cafeId") Long cafeId) {

        log.info("myCafeInfo(): " + cafeId);

        return myPageService.myCafeInfo(cafeId);
    }

//    @PutMapping("/cafe-info-modify/{cafeId}")
//    public void cafeInfoModify(@PathVariable("cafeId") Long cafeId,
//                                                     @RequestBody CafeInfoModifyRequestForm modifyRequest) {
//
//        myPageService.cafeInfoModify(cafeId, modifyRequest);
//    }

    @PutMapping("/add-point/{memberId}")
    public Boolean addPoint(@PathVariable("memberId") Long memberId,
                            @RequestBody AddPointRequestForm pointRequestForm) {

        return myPageService.addPoint(memberId, pointRequestForm);
    }

    @GetMapping("/point-details-list")
    public List<PointDetailsResponse> pointDetailsList() {
        System.out.println("pointDetailsList() 실행");
        return myPageService.pointDetailsList();
    }

    @GetMapping("/point-details/{memberId}")
    public List<PointDetailsResponse> memberPointDetails(@PathVariable("memberId") Long memberId) {
        System.out.println("memberPointDetails() 실행");
        return myPageService.memberPointDetails(memberId);
    }

}
