package fourman.backend.domain.cafeIntroduce.service;

import fourman.backend.domain.cafeIntroduce.controller.requestForm.CafeIntroRequestForm;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroDetailResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroListResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CafeIntroduceService {
    Long registerCafe(List<MultipartFile> thumbnail, List<MultipartFile> fileList, CafeIntroRequestForm cafeIntroRequestForm);

    List<CafeIntroListResponse> list();

    Boolean cafeNumValidation(String code);

    CafeIntroDetailResponse read(Long cafeId);

    Long modifyCafe(Long cafeId,List<MultipartFile> thumbnail, List<MultipartFile> fileList, CafeIntroRequestForm cafeIntroRequestForm);

}
