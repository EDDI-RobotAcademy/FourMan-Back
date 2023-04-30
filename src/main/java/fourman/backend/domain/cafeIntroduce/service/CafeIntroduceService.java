package fourman.backend.domain.cafeIntroduce.service;

import fourman.backend.domain.cafeIntroduce.controller.requestForm.CafeIntroRequestForm;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroDetailResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroListResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeTop3ProductListResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeTop3ProductResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CafeIntroduceService {
    Long registerCafe(List<MultipartFile> thumbnail, List<MultipartFile> fileList, CafeIntroRequestForm cafeIntroRequestForm);

    List<CafeIntroListResponse> list();

    Boolean cafeNumValidation(String code);

    CafeIntroDetailResponse read(Long cafeId);

    Long modifyCafe(Long cafeId,List<MultipartFile> thumbnail, List<MultipartFile> fileList, CafeIntroRequestForm cafeIntroRequestForm);

    void deleteCafe(Long cafeId);

    List<CafeIntroListResponse> favoriteList(long memberId);

    CafeTop3ProductListResponse top3Product(Long cafeId);

}
