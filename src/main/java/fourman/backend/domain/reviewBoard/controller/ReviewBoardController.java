package fourman.backend.domain.reviewBoard.controller;

import fourman.backend.domain.aop.aspect.SecurityAnnotations;
import fourman.backend.domain.reviewBoard.controller.requestForm.ReviewBoardRequestForm;
import fourman.backend.domain.reviewBoard.service.responseForm.ReviewBoardImageResourceResponse;
import fourman.backend.domain.reviewBoard.service.responseForm.ReviewBoardReadResponse;
import fourman.backend.domain.reviewBoard.service.responseForm.ReviewBoardResponse;
import fourman.backend.domain.reviewBoard.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/review-board")
@RequiredArgsConstructor
public class ReviewBoardController {

    final private ReviewBoardService reviewBoardService;

//    AWS s3 사용을 위한 주석 처리
//    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
//    @PostMapping(value = "/register",
//            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
//    public void reviewBoardRegister(
//            // formData 형태로 받아온 상품 이미지파일 리스트와 상품 정보를 @RequestPart로 각각 받아옴
//            @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList,
//            @RequestPart(value = "reviewBoardInfo") ReviewBoardRequestForm reviewBoardRequest) {
//        log.info("reviewBoardRegister()");
//
//        // 받아온 상품 이미지파일 리스트와 상품 정보를 productService의 register 메서드의 매개변수로 넘겨줌
//        reviewBoardService.register(fileList, reviewBoardRequest);
//    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @PostMapping(value = "/register",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public void reviewBoardRegister(
            @RequestPart(value = "ImageFileNameList", required = false) List<String> ImageFileNameList,
            @RequestPart(value = "reviewBoardInfo") ReviewBoardRequestForm reviewBoardRequest) {
        log.info("reviewBoardRegister()");

        reviewBoardService.register(ImageFileNameList, reviewBoardRequest);
    }

    @GetMapping("/list")
    public List<ReviewBoardResponse> reviewBoardList () {
        log.info("reviewBoardList()");

        return reviewBoardService.list();
    }

    @GetMapping("/{reviewBoardId}")
    public ReviewBoardReadResponse reivewBoardRead(@PathVariable("reviewBoardId") Long reviewBoardId) {
        log.info("productRead()");

        return reviewBoardService.read(reviewBoardId);
    }

    @GetMapping("/imageList/{reviewBoardId}")
    public List<ReviewBoardImageResourceResponse> readReviewBoardImageResource(
            @PathVariable("reviewBoardId") Long reviewBoardId) {

        log.info("readProductImageResource(): " + reviewBoardId);

        return reviewBoardService.findReviewBoardImage(reviewBoardId);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @DeleteMapping("/{reviewBoardId}")
    public void reviewBoardRemove(@PathVariable("reviewBoardId") Long reviewBoardId) {
        log.info("productRemove()");

        reviewBoardService.remove(reviewBoardId);
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @PutMapping("/{reviewBoardId}")
    public Boolean reviewBoardModify(@PathVariable("reviewBoardId") Long reviewBoardId,
                                     @RequestBody ReviewBoardRequestForm reviewBoardRequest) {

        log.info("productModify(): " + reviewBoardRequest + "id: " + reviewBoardId);

        return reviewBoardService.modify(reviewBoardId, reviewBoardRequest);
    }

    @GetMapping("/rating/{cafeName}")
    public List<Long> Rating (@PathVariable("cafeName")String cafeName) {
        log.info("Rating()");

        return reviewBoardService.Rating(cafeName);
    }

    @GetMapping("/myPage/{memberId}")
    public List<ReviewBoardResponse> myReviewBoardList(@PathVariable("memberId") Long memberId) {
        return reviewBoardService.myPageList(memberId);
    }
}
