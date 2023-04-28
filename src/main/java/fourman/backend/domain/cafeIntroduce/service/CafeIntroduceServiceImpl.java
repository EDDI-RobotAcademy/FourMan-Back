package fourman.backend.domain.cafeIntroduce.service;
import fourman.backend.domain.cafeIntroduce.controller.requestForm.CafeIntroRequestForm;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.entity.CafeInfo;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroDetailResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroListResponse;
import fourman.backend.domain.eventBoard.entity.Event;
import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.repository.CafeCodeRepository;
import fourman.backend.domain.member.repository.FavoriteRepository;
import fourman.backend.domain.order.repository.OrderInfoRepository;
import fourman.backend.domain.product.repository.ProductRepository;
import fourman.backend.domain.reservation.repository.CafeTableRepository;
import fourman.backend.domain.reservation.repository.ReservationRepository;
import fourman.backend.domain.reservation.repository.SeatRepository;
import fourman.backend.domain.reviewBoard.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Sort;
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
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final CafeTableRepository cafeTableRepository;
    private final FavoriteRepository favoriteRepository;
    private final OrderInfoRepository orderInfoRepository;
    private final ProductRepository productRepository;
    private final ReviewBoardRepository reviewBoardRepository;
    //
    @Override
    public Long registerCafe(List<MultipartFile> thumbnail, List<MultipartFile> fileList, CafeIntroRequestForm cafeIntroRequestForm) {
        // 1. cafe 저장
        Cafe cafe = new Cafe();
        cafe.setCafeAddress(cafeIntroRequestForm.getCafeAddress());
        cafe.setCafeTel(cafeIntroRequestForm.getCafeTel());
        cafe.setStartTime(cafeIntroRequestForm.getStartTime());
        cafe.setEndTime(cafeIntroRequestForm.getEndTime());
        Optional<CafeCode> op= cafeCodeRepository.findByCodeOfCafe(cafeIntroRequestForm.getCode());
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
                String originalFilename = multipartFile.getOriginalFilename();
                String extension = FilenameUtils.getExtension(originalFilename);
                String thumbnailRandomName = now.format(dtf);
                String thumbnailReName = 't' + thumbnailRandomName + "." + extension;

                //저장 경로 지정 + 파일네임
                FileOutputStream writer1 = new FileOutputStream("../FourMan-Front/public/assets/cafe/uploadImgs/" + thumbnailReName);
                log.info("디렉토리에 파일 배치 성공!");

                //파일 저장(저장할때는 byte 형식으로 저장해야 해서 파라미터로 받은 multipartFile 파일들의 getBytes() 메소드 적용해서 저장하는 듯)
                writer1.write(multipartFile.getBytes());
                cafeInfo.setThumbnailFileName(thumbnailReName);
                writer1.close();
            }

            //2. 상품 상세사진 저장
            List<String> imageList = new ArrayList<>();

            for (MultipartFile multipartFile: fileList) {
                String originalFilename = multipartFile.getOriginalFilename();
                String extension = FilenameUtils.getExtension(originalFilename);
                String fileRandomName = now.format(dtf);
                String fileReName = 'f' + fileRandomName + "." + extension;

                FileOutputStream writer2 = new FileOutputStream("../FourMan-Front/public/assets/cafe/uploadImgs/" + fileReName);
                log.info("디렉토리에 파일 배치 성공!");

                writer2.write(multipartFile.getBytes());

                //이미지리스트 이름 저장
                imageList.add(fileReName);

                writer2.close();
            }
            cafeInfo.setCafeImagesName(imageList);
            cafe.setCafeInfo(cafeInfo);
            cafeRepository.save(cafe);
            return cafe.getCafeId();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CafeIntroListResponse> list() {
        List<Cafe> cafeList= cafeRepository.findAll(Sort.by(Sort.Direction.DESC, "cafeId"));
        List<CafeIntroListResponse> cafeReponseList= new ArrayList<>();
        for(Cafe cafe: cafeList){
            cafeReponseList.add(new CafeIntroListResponse(
                    cafe.getCafeId(),cafe.getCafeCode().getCafeName(),cafe.getCafeAddress(),cafe.getCafeTel(),
                    cafe.getStartTime(),cafe.getEndTime(), cafe.getCafeInfo() ));
        }
        return cafeReponseList;
    }

    @Override
    public Boolean cafeNumValidation(String code) {
        Optional<CafeCode> maybeCafeCode = cafeCodeRepository.findByCodeOfCafe(code);
        Optional<Cafe> maybeCafe = cafeRepository.findByCafeCode(maybeCafeCode.get());
        if (maybeCafe.isPresent()) {
            log.info("카페가 존재합니다");
            return true;
        }
        log.info("카페가 존재하지않습니다");
        return false;
    }

    @Override
    public CafeIntroDetailResponse read(Long cafeId) {
        Optional<Cafe> maybeCafe = cafeRepository.findById(cafeId);
        if (maybeCafe.isEmpty()) {
            log.info("카페가 없습니다.");
            return null;
        }
        Cafe cafe = maybeCafe.get();
        CafeIntroDetailResponse cafeIntroDetailResponse = new CafeIntroDetailResponse(
                cafe.getCafeId(),cafe.getCafeCode().getCafeName(),cafe.getCafeAddress(),cafe.getCafeTel(),
                cafe.getStartTime(),cafe.getEndTime(),cafe.getCafeInfo());
        //글내용만 보내기
        log.info("카페read 서비스 완료");
        return cafeIntroDetailResponse;
    }

    @Override
    public Long modifyCafe(Long cafeId, List<MultipartFile> thumbnail, List<MultipartFile> fileList, CafeIntroRequestForm cafeIntroRequestForm) {
        Optional<Cafe> optionalCafe = cafeRepository.findById(cafeId);
        if (!optionalCafe.isPresent()) {
            log.info("카페가 존재하지 않습니다.");
            return null;
        }
        Cafe cafe = optionalCafe.get();
        // 1. cafe 저장
        cafe.setCafeAddress(cafeIntroRequestForm.getCafeAddress());
        cafe.setCafeTel(cafeIntroRequestForm.getCafeTel());
        cafe.setStartTime(cafeIntroRequestForm.getStartTime());
        cafe.setEndTime(cafeIntroRequestForm.getEndTime());
        Optional<CafeCode> maybeCafeCode= cafeCodeRepository.findById(cafe.getCafeCode().getId());
        if (maybeCafeCode.isEmpty()) {
            log.info("카페코드가 존재하지 않습니다.");
            return null;
        }
        cafe.setCafeCode(maybeCafeCode.get());
        //3. cafeInfo 저장
        // cafeInfo-> String thumbnailFileName,List<String> cafeImagesName,List<String>  String subTitle,String description
        CafeInfo cafeInfo= cafe.getCafeInfo();
        cafeInfo.setSubTitle(cafeIntroRequestForm.getSubTitle());
        cafeInfo.setDescription(cafeIntroRequestForm.getDescription());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // 실제 파일 frontend 이미지 폴더 경로에 저장
        try {
            //1. 썸네일 저장
            if (thumbnail != null && !thumbnail.isEmpty()) {
                for (MultipartFile multipartFile : thumbnail) {
                    log.info("requestUploadFilesWithText() - Make file: " + multipartFile.getOriginalFilename());
                    String thumbnailRandomName = now.format(dtf);
                    String thumbnailReName = 't' + thumbnailRandomName + multipartFile.getOriginalFilename();

                    //저장 경로 지정 + 파일네임
                    FileOutputStream writer1 = new FileOutputStream("../FourMan-Front/public/assets/cafe/uploadImgs/" + thumbnailReName);
                    log.info("디렉토리에 파일 배치 성공!");

                    //파일 저장(저장할때는 byte 형식으로 저장해야 해서 파라미터로 받은 multipartFile 파일들의 getBytes() 메소드 적용해서 저장하는 듯)
                    writer1.write(multipartFile.getBytes());
                    cafeInfo.setThumbnailFileName(thumbnailReName);
                    writer1.close();
                }
            }

            //2. 상품 상세사진 저장
            if (fileList != null && !fileList.isEmpty()) {
                List<String> imageList=null;

                if(cafeIntroRequestForm.isAdd()) {//추가
                    imageList= cafeInfo.getCafeImagesName();
                }else{//덮어쓰기
                    imageList= new ArrayList<>();
                }

                for (MultipartFile multipartFile : fileList) {
                    log.info("requestUploadFilesWithText() - Make file: " + multipartFile.getOriginalFilename());

                    String fileRandomName = now.format(dtf);
                    String fileReName = 'f' + fileRandomName + multipartFile.getOriginalFilename();

                    FileOutputStream writer2 = new FileOutputStream("../FourMan-Front/public/assets/cafe/uploadImgs/" + fileReName);
                    log.info("디렉토리에 파일 배치 성공!");
                    writer2.write(multipartFile.getBytes());

                    //이미지리스트 이름 저장
                    imageList.add(fileReName);

                    writer2.close();
                }
                cafeInfo.setCafeImagesName(imageList);
            }
            cafe.setCafeInfo(cafeInfo);
            cafeRepository.save(cafe);
            return cafe.getCafeId();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCafe(Long cafeId) {
        Optional<Cafe> maycafe = cafeRepository.findById(cafeId);
        Cafe cafe=maycafe.get();
        CafeCode cafeCode = cafe.getCafeCode();

        productRepository.deleteByCafeCafeId(cafeId);
        orderInfoRepository.deleteByCafeCafeId(cafeId);
        reviewBoardRepository.deleteByCafeCafeId(cafeId);
        seatRepository.deleteByCafeCafeId(cafeId);
        cafeTableRepository.deleteByCafeCafeId(cafeId);
        reservationRepository.deleteByCafeCafeId(cafeId);
        favoriteRepository.deleteByCafeCafeId(cafeId);
        if (cafeCode != null) {
            cafeCode.setCafe(null);
            cafe.setCafeCode(null);
        }
        cafeRepository.deleteById(cafeId);
    }

    @Override
    public List<CafeIntroListResponse> favoriteList(long memberId) {
            List<Cafe> cafeList= cafeRepository.findCafesByMemberIdOrderByCafeIdDesc(memberId);
            List<CafeIntroListResponse> cafeReponseList= new ArrayList<>();
            for(Cafe cafe: cafeList){
                cafeReponseList.add(new CafeIntroListResponse(
                        cafe.getCafeId(),cafe.getCafeCode().getCafeName(),cafe.getCafeAddress(),cafe.getCafeTel(),
                        cafe.getStartTime(),cafe.getEndTime(), cafe.getCafeInfo() ));
            }
            return cafeReponseList;
    }


}