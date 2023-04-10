package fourman.backend.domain.cafeIntroduce.controller;

import fourman.backend.domain.cafeIntroduce.controller.requestForm.CafeIntroRequestForm;
import fourman.backend.domain.cafeIntroduce.service.CafeIntroduceService;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroDetailResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/cafe")
@RequiredArgsConstructor
public class CafeIntroduceController {

    private final CafeIntroduceService cafeIntroduceService;

    @PostMapping(value = "/register",
            consumes = {  MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE }) // 이미지+텍스트 업로드하는 경우 value , consumes 정보(이미지타입, json타입) 추가
    public String registerCafe(
            @RequestPart(value = "thumbnail") List<MultipartFile> thumbnail,
            @RequestPart(value = "fileList") List<MultipartFile> fileList,
            @RequestPart(value = "info") CafeIntroRequestForm cafeIntroRequestForm) {

        log.info("카페등록 컨트롤러-파일리스트: " + fileList.toString());
        log.info("카페등록 컨트롤러-리퀘스트내용: " + cafeIntroRequestForm);

        cafeIntroduceService.registerCafe(thumbnail, fileList, cafeIntroRequestForm);

        return "카페가 등록되었습니다.";
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

}
