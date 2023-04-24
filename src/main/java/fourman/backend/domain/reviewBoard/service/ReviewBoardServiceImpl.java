package fourman.backend.domain.reviewBoard.service;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.CafeCodeRepository;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.reviewBoard.controller.requestForm.ReviewBoardRequestForm;
import fourman.backend.domain.reviewBoard.service.responseForm.ReviewBoardImageResourceResponse;
import fourman.backend.domain.reviewBoard.service.responseForm.ReviewBoardReadResponse;
import fourman.backend.domain.reviewBoard.service.responseForm.ReviewBoardResponse;
import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import fourman.backend.domain.reviewBoard.entity.ReviewBoardImageResource;
import fourman.backend.domain.reviewBoard.repository.ReviewBoardImageResourceRepository;
import fourman.backend.domain.reviewBoard.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
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
@Service
@RequiredArgsConstructor
public class ReviewBoardServiceImpl implements ReviewBoardService {

    final private ReviewBoardRepository reviewBoardRepository;

    final private ReviewBoardImageResourceRepository reviewBoardImageResourceRepository;
    final private MemberRepository memberRepository;
    final private CafeRepository cafeRepository;
    final private CafeCodeRepository cafeCodeRepository;

    @Transactional
    @Override
    public void register(List<MultipartFile> fileList, ReviewBoardRequestForm reviewBoardRequest) {
        log.info("글자 출력: " + reviewBoardRequest);

        List<ReviewBoardImageResource> reviewBoardImageResourceList = new ArrayList<>();

        // 현재 경로를 기준으로 프론트 엔드의 uploadImgs로 상대경로 값을 문자열로 저장함 (파일을 저장할 경로)
        final String fixedStringPath = "../FourMan-Front/src/assets/reviewImage/";

        ReviewBoard reviewBoard = new ReviewBoard();

        // 카페 이름으로 카페 코드를 받아와 해당 카페 객체 저장
        Optional<CafeCode> maybeCafeCode = cafeCodeRepository.findCafeIdByCafeName(reviewBoardRequest.getCafeName());

        if(maybeCafeCode.isEmpty()) {

        }

        CafeCode cafeCode = maybeCafeCode.get();
        Optional<Cafe> maybeCafe = cafeRepository.findByCafeCode(cafeCode);

        if(maybeCafe.isEmpty()) {

        }

        Cafe cafe = maybeCafe.get();

        // 받아온 상품정보 값 setting
        reviewBoard.setCafe(cafe);
        reviewBoard.setTitle(reviewBoardRequest.getTitle());
        reviewBoard.setContent(reviewBoardRequest.getContent());
        reviewBoard.setRating(reviewBoardRequest.getRating());

        Optional<Member> maybeMember = memberRepository.findById(reviewBoardRequest.getMemberId());

        if(maybeMember.isEmpty()) {

        }
        reviewBoard.setMember(maybeMember.get());

        String content = reviewBoard.getContent();

        content = content.replaceAll("!\\[[^\\]]*\\]\\([^)]*\\)", ""); // <img> 태그 제거

        // base64로 디코딩 하지 않고 단순히 <img> <p> 태그 replace 후 저장
        reviewBoard.setContent(content);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        if(fileList != null) {
            try {
                for (MultipartFile multipartFile: fileList) {
                    log.info("requestFileUploadWithText() - filename: " + multipartFile.getOriginalFilename());

                    String thumbnailRandomName = now.format(dtf);
                    String thumbnailReName = 't'+thumbnailRandomName + multipartFile.getOriginalFilename();

                    // 파일 저장 위치에 파일 이름을 더해 fullPath 문자열 저장
                    String fullPath = fixedStringPath + thumbnailReName;


                    FileOutputStream writer = new FileOutputStream(fullPath);

                    writer.write(multipartFile.getBytes());
                    writer.close();

                    // 이미지 경로를 DB에 저장할때 경로를 제외한 이미지파일 이름만 저장하도록 함 (프론트에서 경로 지정하여 사용하기 위함)
                    ReviewBoardImageResource reviewBoardImageResource = new ReviewBoardImageResource(thumbnailReName);
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

    @Transactional
    @Override
    public List<ReviewBoardResponse> list() {
        // DB에서 모든 상품을 불러와 리스트에 저장
        List<ReviewBoard> reviewBoardList = reviewBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "reviewBoardId"));
        // 응답 파일 리스트를 응답할 리스트 생성
        List<ReviewBoardResponse> reviewBoardResponseList = new ArrayList<>();

        // 불러온 상품 리스트를 반복문을 통해 productResponseList에 추가
        for (ReviewBoard reviewBoard: reviewBoardList) {
            String firstPhoto = null;
            List<ReviewBoardImageResource> images = reviewBoardImageResourceRepository.findAllImagesByReviewBoardId(reviewBoard.getReviewBoardId());
            if (!images.isEmpty()) {
                firstPhoto = images.get(0).getReviewBoardImageResourcePath();
            }

            reviewBoardResponseList.add(new ReviewBoardResponse(
                    reviewBoard.getReviewBoardId(), reviewBoard.getCafe().getCafeCode().getCafeName(), reviewBoard.getTitle(),
                    reviewBoard.getMember().getNickName(), reviewBoard.getContent(), reviewBoard.getRating(),
                    reviewBoard.getMember().getId(), reviewBoard.getRegDate(), reviewBoard.getUpdDate(), firstPhoto
            ));
        }

        // 추가한 reviewBoardResponseList를 반환
        return reviewBoardResponseList;
    }

    @Transactional
    @Override
    public ReviewBoardReadResponse read(Long reviewBoardId) {
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
        ReviewBoardReadResponse reviewBoardReadResponse = new ReviewBoardReadResponse(
                reviewBoard.getReviewBoardId(), reviewBoard.getCafe().getCafeCode().getCafeName(), reviewBoard.getTitle(), reviewBoard.getMember().getNickName(),
                reviewBoard.getContent(), reviewBoard.getRegDate(), reviewBoard.getMember().getId(), reviewBoard.getRating()
        );

        // productReadResponse 응답
        return reviewBoardReadResponse;
    }

    @Override
    public List<ReviewBoardImageResourceResponse> findReviewBoardImage(Long reviewBoardId) {
        List<ReviewBoardImageResource> reviewBoardImageResourceList = reviewBoardImageResourceRepository.findImagePathByReviewBoardId(reviewBoardId);
        List<ReviewBoardImageResourceResponse> reviewBoardImageResourceResponseList = new ArrayList<>();

        for (ReviewBoardImageResource reviewBoardImageResource: reviewBoardImageResourceList) {
            System.out.println("imageResource path: " + reviewBoardImageResource.getReviewBoardImageResourcePath());

            reviewBoardImageResourceResponseList.add(new ReviewBoardImageResourceResponse(
                    reviewBoardImageResource.getReviewBoardImageResourcePath()));
        }

        return reviewBoardImageResourceResponseList;
    }

    @Override
    public void remove(Long reviewBoardId) {

        // 상품 아이디로 해당 상품 이미지 파일 정보 리스트로 가져옴
        List<ReviewBoardImageResource> imagePath = reviewBoardImageResourceRepository.findImagePathByReviewBoardId(reviewBoardId);

        // 가져온 이미지 파일 리스트 반복문을 통해 DB와 로컬에 저장되어있는 이미지 파일 삭제
        for (ReviewBoardImageResource i: imagePath) {
            reviewBoardImageResourceRepository.deleteById(i.getId());

            String filePath = "../../FourMan-Front/frontend/src/assets/reviewImage/" + i.getReviewBoardImageResourcePath();
            File file = new File(filePath);
            file.delete();
        }
        reviewBoardRepository.deleteById(reviewBoardId);
    }

    @Override
    public Boolean modify(Long reviewBoardId, ReviewBoardRequestForm reviewBoardRequest) {
        Optional<ReviewBoard> maybeReviewBoard = reviewBoardRepository.findById(reviewBoardId);

        if (maybeReviewBoard.isEmpty()) {
            System.out.println("ReviewBoard 정보를 찾지 못했습니다: " + reviewBoardId);
            return false;
        }

        ReviewBoard reviewBoard = maybeReviewBoard.get();
        reviewBoard.setTitle(reviewBoardRequest.getTitle());
        reviewBoard.setContent(reviewBoardRequest.getContent());
        reviewBoard.setRating(reviewBoardRequest.getRating());

        reviewBoardRepository.save(reviewBoard);

        return true;
    }

    @Override
    public List<Long> Rating(String cafeName) {

        List<ReviewBoard> reviewBoardList = reviewBoardRepository.findByCafeName(cafeName);
        List<Long> ratingList = new ArrayList<>();

        for(ReviewBoard reviewBoard: reviewBoardList) {
            Long rating = reviewBoard.getRating();
            ratingList.add(rating);
        }

        return ratingList;
    }

    @Transactional
    @Override
    public List<ReviewBoardResponse> myPageList(Long memberId) {
        List<ReviewBoard> reviewBoardList = reviewBoardRepository.findReviewBoardByMemberId(memberId);

        // 응답 파일 리스트를 응답할 리스트 생성
        List<ReviewBoardResponse> reviewBoardResponseList = new ArrayList<>();

        for (ReviewBoard reviewBoard: reviewBoardList) {
            String firstPhoto = null;
            List<ReviewBoardImageResource> images = reviewBoardImageResourceRepository.findAllImagesByReviewBoardId(reviewBoard.getReviewBoardId());
            if (!images.isEmpty()) {
                firstPhoto = images.get(0).getReviewBoardImageResourcePath();
            }



            reviewBoardResponseList.add(new ReviewBoardResponse(
                    reviewBoard.getReviewBoardId(), reviewBoard.getCafe().getCafeCode().getCafeName(), reviewBoard.getTitle(),
                    reviewBoard.getMember().getNickName(), reviewBoard.getContent(), reviewBoard.getRating(),
                    reviewBoard.getMember().getId(), reviewBoard.getRegDate(), reviewBoard.getUpdDate(), firstPhoto
            ));
        }

        // 추가한 reviewBoardResponseList를 반환
        return reviewBoardResponseList;
    }

}
