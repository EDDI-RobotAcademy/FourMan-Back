package fourman.backend.domain.reviewBoard.service;

import fourman.backend.domain.reviewBoard.controller.requestForm.ReviewBoardRequestForm;
import fourman.backend.domain.reviewBoard.controller.responseForm.ReviewBoardImageResourceResponseForm;
import fourman.backend.domain.reviewBoard.controller.responseForm.ReviewBoardReadResponseForm;
import fourman.backend.domain.reviewBoard.controller.responseForm.ReviewBoardResponseForm;
import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import fourman.backend.domain.reviewBoard.entity.ReviewBoardImageResource;
import fourman.backend.domain.reviewBoard.repository.ReviewBoardImageResourceRepository;
import fourman.backend.domain.reviewBoard.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewBoardServiceImpl implements ReviewBoardService {

    final private ReviewBoardRepository reviewBoardRepository;

    final private ReviewBoardImageResourceRepository reviewBoardImageResourceRepository;

    @Transactional
    @Override
    public void register(List<MultipartFile> fileList, ReviewBoardRequestForm reviewBoardRequest) {
        log.info("글자 출력: " + reviewBoardRequest);

        List<ReviewBoardImageResource> reviewBoardImageResourceList = new ArrayList<>();

        // 현재 경로를 기준으로 프론트 엔드의 uploadImgs로 상대경로 값을 문자열로 저장함 (파일을 저장할 경로)
        final String fixedStringPath = "../../FourMan-Front/frontend/src/assets/reviewImage/";

        ReviewBoard reviewBoard = new ReviewBoard();

        // 받아온 상품정보 값 setting
        reviewBoard.setCafeName(reviewBoardRequest.getCafeName());
        reviewBoard.setTitle(reviewBoardRequest.getTitle());
        reviewBoard.setWriter(reviewBoardRequest.getWriter());
        reviewBoard.setContent(reviewBoardRequest.getContent());
        reviewBoard.setRating(reviewBoardRequest.getRating());
        reviewBoard.setMemberId(reviewBoardRequest.getMemberId());

        if(fileList != null) {
            try {
                for (MultipartFile multipartFile: fileList) {
                    log.info("requestFileUploadWithText() - filename: " + multipartFile.getOriginalFilename());

                    // 파일 저장 위치에 파일 이름을 더해 fullPath 문자열 저장
                    String fullPath = fixedStringPath + multipartFile.getOriginalFilename();


                    FileOutputStream writer = new FileOutputStream(fullPath);

                    writer.write(multipartFile.getBytes());
                    writer.close();

                    // 이미지 경로를 DB에 저장할때 경로를 제외한 이미지파일 이름만 저장하도록 함 (프론트에서 경로 지정하여 사용하기 위함)
                    ReviewBoardImageResource reviewBoardImageResource = new ReviewBoardImageResource(multipartFile.getOriginalFilename());
                    reviewBoardImageResourceList.add(reviewBoardImageResource);
                    reviewBoard.setReviewBoardImageResource(reviewBoardImageResource);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            reviewBoardImageResourceRepository.saveAll(reviewBoardImageResourceList);
        }

        reviewBoardRepository.save(reviewBoard);
    }

    @Override
    public List<ReviewBoardResponseForm> list() {
        // DB에서 모든 상품을 불러와 리스트에 저장
        List<ReviewBoard> reviewBoardList = reviewBoardRepository.findAll();
        // 응답 파일 리스트를 응답할 리스트 생성
        List<ReviewBoardResponseForm> reviewBoardResponseList = new ArrayList<>();

        // 불러온 상품 리스트를 반복문을 통해 productResponseList에 추가
        for (ReviewBoard reviewBoard: reviewBoardList) {
            String firstPhoto = null;
            List<ReviewBoardImageResource> images = reviewBoardImageResourceRepository.findAllImagesByReviewBoardId(reviewBoard.getReviewBoardId());
            if (!images.isEmpty()) {
                firstPhoto = images.get(0).getReviewBoardImageResourcePath();
            }

            reviewBoardResponseList.add(new ReviewBoardResponseForm(
                    reviewBoard.getReviewBoardId(), reviewBoard.getCafeName(), reviewBoard.getTitle(),
                    reviewBoard.getWriter(), reviewBoard.getContent(), reviewBoard.getRating(),
                    reviewBoard.getMemberId(), reviewBoard.getRegDate(), reviewBoard.getUpdDate(), firstPhoto
            ));
        }

        // 추가한 reviewBoardResponseList를 반환
        return reviewBoardResponseList;
    }

    @Override
    public ReviewBoardReadResponseForm read(Long reviewBoardId) {
        // 매개변수로 받아온 상품 아이디를 조건으로 DB에서 상품 정보를 불러와 maybeProduct에 저장
        Optional<ReviewBoard> maybeReviewBoard = reviewBoardRepository.findById(reviewBoardId);

        // maybeProduct 값이 비어있다면 null을 리턴
        if (maybeReviewBoard.isEmpty()) {
            log.info("읽을 수가 없드아!");
            return null;
        }

        // 값이 있다면 product 객체에 값을 저장
        ReviewBoard reviewBoard = maybeReviewBoard.get();

        // 상품 상세 정보를 Response 해줄 객체에 정보를 담음
        ReviewBoardReadResponseForm reviewBoardReadResponseForm = new ReviewBoardReadResponseForm(
                reviewBoard.getReviewBoardId(), reviewBoard.getCafeName(), reviewBoard.getTitle(), reviewBoard.getWriter(),
                reviewBoard.getContent(), reviewBoard.getRegDate()
        );

        // productReadResponse 응답
        return reviewBoardReadResponseForm;
    }

    @Override
    public List<ReviewBoardImageResourceResponseForm> findReviewBoardImage(Long reviewBoardId) {
        List<ReviewBoardImageResource> reviewBoardImageResourceList = reviewBoardImageResourceRepository.findImagePathByReviewBoardId(reviewBoardId);
        List<ReviewBoardImageResourceResponseForm> reviewBoardImageResourceResponseFormList = new ArrayList<>();

        for (ReviewBoardImageResource reviewBoardImageResource: reviewBoardImageResourceList) {
            System.out.println("imageResource path: " + reviewBoardImageResource.getReviewBoardImageResourcePath());

            reviewBoardImageResourceResponseFormList.add(new ReviewBoardImageResourceResponseForm(
                    reviewBoardImageResource.getReviewBoardImageResourcePath()));
        }

        return reviewBoardImageResourceResponseFormList;
    }

}
