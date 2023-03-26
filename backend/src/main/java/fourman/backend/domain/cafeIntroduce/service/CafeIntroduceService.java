package fourman.backend.domain.cafeIntroduce.service;

import fourman.backend.domain.cafeIntroduce.controller.requestForm.CafeIntroRequestForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CafeIntroduceService {
    public void registerCafe(List<MultipartFile> thumbnail, List<MultipartFile> fileList, CafeIntroRequestForm cafeIntroRequestForm);
}
