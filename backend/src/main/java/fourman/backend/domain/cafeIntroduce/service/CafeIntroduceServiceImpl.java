package fourman.backend.domain.cafeIntroduce.service;
import fourman.backend.domain.cafeIntroduce.controller.requestForm.CafeIntroRequestForm;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.entity.CafeInfo;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CafeIntroduceServiceImpl implements CafeIntroduceService {
    private final CafeRepository cafeRepository;
    @Override
    public void registerCafe(List<MultipartFile> thumbnail, List<MultipartFile> fileList, CafeIntroRequestForm cafeIntroRequestForm) {
        // 1. cafe 저장
        Cafe cafe = new Cafe();
        cafe.setCafeName(cafeIntroRequestForm.getCafeName());
        cafe.setCafeAddress(cafeIntroRequestForm.getCafeAddress());
        cafe.setCafeTel(cafeIntroRequestForm.getCafeTel());
        cafe.setStartTime(cafeIntroRequestForm.getStartTime());
        cafe.setEndTime(cafeIntroRequestForm.getEndTime());

        //3. cafeInfo 저장
        // cafeInfo-> String thumbnailFileName,List<String> cafeImagesName,List<String>  String subTitle,String description
        CafeInfo cafeInfo = new CafeInfo();
        cafeInfo.setSubTitle(cafeIntroRequestForm.getSubTitle());
        cafeInfo.setDescription(cafeIntroRequestForm.getDescription());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // 실제 파일 frontend 이미지 폴더 경로에 저장
        try {
            //1. 썸네일 저장
            for (MultipartFile multipartFile: thumbnail) {
                log.info("requestUploadFilesWithText() - Make file: " + multipartFile.getOriginalFilename());
                String thumbnailRandomName = now.format(dtf);
                String thumbnailReName = 't'+thumbnailRandomName + multipartFile.getOriginalFilename();

                //저장 경로 지정 + 파일네임
                FileOutputStream writer1 = new FileOutputStream("../../FourMan-Front/frontend/src/assets/cafe/uploadImg/" + thumbnailReName);
                log.info("디렉토리에 파일 배치 성공!");

                //파일 저장(저장할때는 byte 형식으로 저장해야 해서 파라미터로 받은 multipartFile 파일들의 getBytes() 메소드 적용해서 저장하는 듯)
                writer1.write(multipartFile.getBytes());
                cafeInfo.setThumbnailFileName(thumbnailReName);
                writer1.close();
            }

            //2. 상품 상세사진 저장
            List<String> imageList = new ArrayList<>();

            for (MultipartFile multipartFile: fileList) {
                log.info("requestUploadFilesWithText() - Make file: " + multipartFile.getOriginalFilename());

                String fileRandomName = now.format(dtf);
                String fileReName = 'f' + fileRandomName + multipartFile.getOriginalFilename();

                FileOutputStream writer2 = new FileOutputStream("../../FourMan-Front/frontend/src/assets/cafe/uploadImg/" + fileReName);
                log.info("디렉토리에 파일 배치 성공!");

                writer2.write(multipartFile.getBytes());

                //이미지리스트 이름 저장
                imageList.add(fileReName);

                writer2.close();
            }
            cafeInfo.setCafeImagesName(imageList);
            cafe.setCafeInfo(cafeInfo);
            cafeRepository.save(cafe);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
