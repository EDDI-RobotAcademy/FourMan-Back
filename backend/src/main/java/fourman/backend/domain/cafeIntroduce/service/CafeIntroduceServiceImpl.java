package fourman.backend.domain.cafeIntroduce.service;
import fourman.backend.domain.cafeIntroduce.controller.requestForm.CafeIntroRequestForm;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.entity.CafeInfo;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.repository.CafeCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CafeIntroduceServiceImpl implements CafeIntroduceService {
    private final CafeRepository cafeRepository;
    private final CafeCodeRepository cafeCodeRepository;
    //
    @Override
    public void registerCafe(List<MultipartFile> thumbnail, List<MultipartFile> fileList, CafeIntroRequestForm cafeIntroRequestForm) {
        // 1. cafe 저장
        Cafe cafe = new Cafe();
        cafe.setCafeName(cafeIntroRequestForm.getCafeName());
        cafe.setCafeAddress(cafeIntroRequestForm.getCafeAddress());
        cafe.setCafeTel(cafeIntroRequestForm.getCafeTel());
        cafe.setStartTime(cafeIntroRequestForm.getStartTime());
        cafe.setEndTime(cafeIntroRequestForm.getEndTime());
        Optional<CafeCode> op= cafeCodeRepository.findByCode(cafeIntroRequestForm.getCode());
        cafe.setCafeCode(op.get());
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
                FileOutputStream writer1 = new FileOutputStream("../../FourMan-Front/frontend/src/assets/cafe/uploadImgs/" + thumbnailReName);
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

                FileOutputStream writer2 = new FileOutputStream("../../FourMan-Front/frontend/src/assets/cafe/uploadImgs/" + fileReName);
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
