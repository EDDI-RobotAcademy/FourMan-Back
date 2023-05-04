package fourman.backend.domain.cafeIntroduce.controller;

import fourman.backend.domain.aop.aspect.SecurityAnnotations;
import fourman.backend.domain.cafeIntroduce.controller.requestForm.CafeIntroRequestForm;
import fourman.backend.domain.cafeIntroduce.service.CafeIntroduceService;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroDetailResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroListResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeTop3ProductListResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeTop3ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/cafe")
@RequiredArgsConstructor
public class CafeIntroduceController {

    private final CafeIntroduceService cafeIntroduceService;
//    @AWS S3 업로드를 위한 주석처리
//    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
//    @PostMapping(value = "/register",
//            consumes = {  MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE }) // 이미지+텍스트 업로드하는 경우 value , consumes 정보(이미지타입, json타입) 추가
//    public Long registerCafe(
//            @RequestPart(value = "thumbnail") List<MultipartFile> thumbnail,
//            @RequestPart(value = "fileList") List<MultipartFile> fileList,
//            @RequestPart(value = "info") CafeIntroRequestForm cafeIntroRequestForm) {
//
//        log.info("카페등록 컨트롤러-파일리스트: " + fileList.toString());
//        log.info("카페등록 컨트롤러-리퀘스트내용: " + cafeIntroRequestForm);
//
//        return cafeIntroduceService.registerCafe(thumbnail, fileList, cafeIntroRequestForm);
//    }
    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @PostMapping(value = "/register",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long registerCafe(
            @RequestPart(value = "thumbnailFileNameList") List<String> thumbnailFileNameList,
            @RequestPart(value = "multipleFileNameList") List<String> multipleFileNameList,
            @RequestPart(value = "info") CafeIntroRequestForm cafeIntroRequestForm) {

        log.info("카페등록 컨트롤러-리퀘스트내용: " + cafeIntroRequestForm);

        return cafeIntroduceService.AWSregisterCafe(thumbnailFileNameList, multipleFileNameList, cafeIntroRequestForm);
    }



    @GetMapping(path = "/list")
    public List<CafeIntroListResponse> cafeList() {
            return cafeIntroduceService.list();
        }

    @PostMapping("/check-code/{code}")// 카페코드로 카페등록을 이미 했는지 확인
    public Boolean cafeNumValidation(@PathVariable("code") String code) {
        log.info("cafeNumValidation(): " + code);

        return cafeIntroduceService.cafeNumValidation(code);
    }

    @GetMapping("detail/{cafeId}")
    public CafeIntroDetailResponse cafeRead(@PathVariable("cafeId") Long cafeId) {
        log.info("cafeRead()");
        return cafeIntroduceService.read(cafeId);
    }
    
//AWS를 위한 주석처리
//    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
//    @PutMapping(value = "/modify/{cafeId}",
//            consumes = {  MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE }) // 이미지+텍스트 업로드하는 경우 value , consumes 정보(이미지타입, json타입) 추가
//    public Long modifyEvent(
//            @RequestPart(value = "thumbnail", required = false) List<MultipartFile> thumbnail,
//            @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList,
//            @RequestPart(value = "info") CafeIntroRequestForm cafeIntroRequestForm,
//            @PathVariable("cafeId") Long cafeId ) {
//
//        log.info("카페 수정 컨트롤러-리퀘스트내용: " + cafeIntroRequestForm);
//
//        return cafeIntroduceService.modifyCafe(cafeId,thumbnail, fileList, cafeIntroRequestForm);
//    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @PutMapping(value = "/modify/{cafeId}",
            consumes = {  MediaType.MULTIPART_FORM_DATA_VALUE }) // 이미지+텍스트 업로드하는 경우 value , consumes 정보(이미지타입, json타입) 추가
    public Long modifyEvent(
            @RequestPart(value = "thumbnailFileNameList" , required = false) List<String> thumbnailFileNameList,
            @RequestPart(value = "multipleFileNameList", required = false) List<String> multipleFileNameList,
            @RequestPart(value = "info") CafeIntroRequestForm cafeIntroRequestForm,
            @PathVariable("cafeId") Long cafeId ) {

        log.info("카페 수정 컨트롤러-리퀘스트내용: " + cafeIntroRequestForm);

        return cafeIntroduceService.AWSmodifyCafe(cafeId, thumbnailFileNameList, multipleFileNameList, cafeIntroRequestForm);
    }



    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @DeleteMapping("/delete/{cafeId}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long cafeId) {
        log.info("cafe 삭제 컨트롤러");
        cafeIntroduceService.deleteCafe(cafeId);
        return ResponseEntity.ok().build();
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @GetMapping(path = "/list/{memberId}")
    public List<CafeIntroListResponse> cafeFavoriteList(@PathVariable("memberId") Long memberId) {
        return cafeIntroduceService.favoriteList(memberId);
    }

    @GetMapping("top3/{cafeId}")
    public CafeTop3ProductListResponse top3Product(@PathVariable("cafeId") Long cafeId) {
        log.info("top3Product");
        return cafeIntroduceService.top3Product(cafeId);
    }

}
