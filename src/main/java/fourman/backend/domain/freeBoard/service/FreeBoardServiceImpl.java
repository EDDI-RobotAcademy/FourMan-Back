package fourman.backend.domain.freeBoard.service;

import fourman.backend.domain.freeBoard.controller.requestForm.FreeBoardRequestForm;
import fourman.backend.domain.freeBoard.controller.requestForm.RecommendationRequestForm;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.entity.FreeBoardImageResource;
import fourman.backend.domain.freeBoard.entity.Recommendation;
import fourman.backend.domain.freeBoard.repository.FreeBoardImageResourceRepository;
import fourman.backend.domain.freeBoard.repository.FreeBoardRepository;
import fourman.backend.domain.freeBoard.repository.RecommendataionRepository;
import fourman.backend.domain.freeBoard.service.responseForm.FreeBoardImageResourceResponse;
import fourman.backend.domain.freeBoard.service.responseForm.FreeBoardReadResponse;
import fourman.backend.domain.freeBoard.service.responseForm.FreeBoardResponse;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
public class FreeBoardServiceImpl implements FreeBoardService {

    final private FreeBoardRepository freeBoardRepository;

    final private MemberRepository memberRepository;
    final private FreeBoardImageResourceRepository freeBoardImageResourceRepository;
    final private RecommendataionRepository recommendationRepository;

    @Transactional
    @Override
    public FreeBoard register(List<MultipartFile> fileList, FreeBoardRequestForm freeBoardRequest) {

        List<FreeBoardImageResource> freeBoardImageResourceList = new ArrayList<>();

        // 현재 경로를 기준으로 프론트 엔드의 uploadImgs로 상대경로 값을 문자열로 저장함 (파일을 저장할 경로)
        final String fixedStringPath = "../FourMan-Front/src/assets/freeBoardImages/";

        FreeBoard freeBoard = new FreeBoard();
        freeBoard.setTitle(freeBoardRequest.getTitle());
        freeBoard.setContent(freeBoardRequest.getContent());

        Optional<Member> maybeMember = memberRepository.findById(freeBoardRequest.getMemberId());

        if (maybeMember.isEmpty()) {
            return null;
        }
        freeBoard.setMember(maybeMember.get());

        String content = freeBoard.getContent();

        content = content.replaceAll("!\\[[^\\]]*\\]\\([^)]*\\)", ""); // <img> 태그 제거

        // base64로 디코딩 하지 않고 단순히 <img> <p> 태그 replace 후 저장
        freeBoard.setContent(content);


        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        if (fileList != null) {
            try {
                for (MultipartFile multipartFile : fileList) {
                    log.info("requestFileUploadWithText() - filename: " + multipartFile.getOriginalFilename());

                    String thumbnailRandomName = now.format(dtf);
                    String thumbnailReName = 't' + thumbnailRandomName + multipartFile.getOriginalFilename();

                    // 파일 저장 위치에 파일 이름을 더해 fullPath 문자열 저장
                    String fullPath = fixedStringPath + thumbnailReName;


                    FileOutputStream writer = new FileOutputStream(fullPath);

                    writer.write(multipartFile.getBytes());
                    writer.close();

                    // 이미지 경로를 DB에 저장할때 경로를 제외한 이미지파일 이름만 저장하도록 함 (프론트에서 경로 지정하여 사용하기 위함)
                    FreeBoardImageResource freeBoardImageResource = new FreeBoardImageResource(thumbnailReName);
                    freeBoardImageResourceList.add(freeBoardImageResource);
                    freeBoard.setFreeBoardImageResource(freeBoardImageResource);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            freeBoardImageResourceRepository.saveAll(freeBoardImageResourceList);
        }

        freeBoard.setViewCnt(0L);
        freeBoard.setRecommendation(0L);
        freeBoard.setUnRecommendation(0L);
        freeBoardRepository.save(freeBoard);

        return freeBoard;
    }
    @Override
    @Transactional
    public List<FreeBoardResponse> list() {
        List<FreeBoard> freeBoardList = freeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
        List<FreeBoardResponse> freeBoardResponseList = new ArrayList<>();

        for (FreeBoard freeBoard : freeBoardList) {
            Long commentCount = (long) freeBoard.getFreeBoardCommentList().size();
            FreeBoardResponse freeBoardResponse = new FreeBoardResponse(
                    freeBoard.getBoardId(), freeBoard.getTitle(), freeBoard.getMember().getNickName(), freeBoard.getContent(),
                    freeBoard.getRegDate(), freeBoard.getUpdDate(), freeBoard.getMember().getId(), freeBoard.getViewCnt(), freeBoard.getRecommendation(),
                    freeBoard.getUnRecommendation(), commentCount);
            freeBoardResponseList.add(freeBoardResponse);
        }
        return freeBoardResponseList;
    }


    @Transactional
    @Override
    public FreeBoardReadResponse read(Long boardId, Long memberId) {
        // 일 수도 있고 아닐 수도 있고
        Optional<FreeBoard> maybeBoard = freeBoardRepository.findById(boardId);
        if (maybeBoard.isEmpty()) {
            log.info("읽을 수가 없드아!");
            return null;
        }
        FreeBoard freeBoard = maybeBoard.get();
        freeBoard.increaseViewCnt();
        freeBoardRepository.save(freeBoard);

        Recommendation recommendation = recommendationRepository.findByFreeBoardAndMemberId(freeBoard, memberId);

        boolean incRecommendationStatus = false;
        boolean decRecommendationStatus = false;

        if(recommendation != null) {
            incRecommendationStatus = recommendation.isIncRecommendationStatus();
            decRecommendationStatus = recommendation.isDecRecommendationStatus();
        }

        FreeBoardReadResponse freeBoardReadResponse = new FreeBoardReadResponse(
                freeBoard.getBoardId(), freeBoard.getTitle(), freeBoard.getMember().getNickName(), freeBoard.getContent(),
                freeBoard.getRegDate(), freeBoard.getUpdDate(), freeBoard.getMember().getId(), freeBoard.getViewCnt(), freeBoard.getRecommendation(),
                freeBoard.getUnRecommendation(),0L, incRecommendationStatus, decRecommendationStatus
        );

        return freeBoardReadResponse;
    }

    @Override
    public void remove(Long boardId) {
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findById(boardId);
        if (maybeFreeBoard.isPresent()) {
            FreeBoard freeBoard = maybeFreeBoard.get();

            List<Recommendation> recommendations = recommendationRepository.findAllByFreeBoard(freeBoard);
            if (!recommendations.isEmpty()) {
                recommendationRepository.deleteAll(recommendations);
            }

            // 이제 게시물을 삭제합니다.
            freeBoardRepository.deleteById(boardId);
        }
    }

    @Override
    public Boolean modify(Long boardId, FreeBoardRequestForm boardRequest) {
        Optional<FreeBoard> maybeBoard = freeBoardRepository.findById(boardId);
        if (maybeBoard.isEmpty()) {
            System.out.println("Board 정보를 찾지 못했습니다: " + boardId);
            return false;
        }
        FreeBoard freeBoard = maybeBoard.get();
        freeBoard.setTitle(boardRequest.getTitle());
        freeBoard.setContent(boardRequest.getContent());
        freeBoardRepository.save(freeBoard);
        return true;
    }

    @Override
    public List<FreeBoard> myPageList(Long memberId) {
        return freeBoardRepository.findFreeBoardByMemberId(memberId);
    }

    @Override
    public List<FreeBoard> searchFreeBoardList(String searchText) {
        return freeBoardRepository.findSearchFreeBoardBySearchText(searchText);
    }

//    @Override
//    public Long incRecommendation(Long boardId) {
//        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findById(boardId);
//        if(maybeFreeBoard.isEmpty()) {
//            return null;
//        }
//        FreeBoard freeBoard = maybeFreeBoard.get();
//        freeBoard.increaseRecommendation();
//        freeBoardRepository.save(freeBoard);
//        return freeBoard.getRecommendation();
//    }
//@Override
//public Long decRecommendation(Long boardId) {
//    Optional<FreeBoard> maybeFreeboard = freeBoardRepository.findById(boardId);
//    if(maybeFreeboard.isEmpty()) {
//        return null;
//    }
//    FreeBoard freeBoard = maybeFreeboard.get();
//    freeBoard.decreaseRecommendation();
//    freeBoardRepository.save(freeBoard);
//    return freeBoard.getRecommendation();
//}


    @Transactional
    @Override
    public Long upRecommendation(Long boardId, RecommendationRequestForm recommendationRequestForm) {
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findById(boardId);
        if (maybeFreeBoard.isEmpty()) {
            return null;
        }
        FreeBoard freeBoard = maybeFreeBoard.get();

        Optional<Member> maybeMember = memberRepository.findById(recommendationRequestForm.getMemberId());
        if (maybeMember.isEmpty()) {
            return null;
        }
        Member member = maybeMember.get();

        Recommendation findRecommendation = recommendationRepository.findByFreeBoardAndMemberId(freeBoard, member.getId());
        if (findRecommendation == null) {
            Recommendation recommendation = new Recommendation(freeBoard, member);
            recommendation.incRecommendation();
            recommendation.setIncRecommendationStatus(true);
            recommendationRepository.save(recommendation);
            return freeBoard.getRecommendation();
        } else {
            if (findRecommendation.isIncRecommendationStatus()) {
                Recommendation recommendation = recommendationRepository.findByFreeBoardAndMemberId(freeBoard, member.getId());
                recommendation.decRecommendation();
                recommendationRepository.delete(recommendation);
                return freeBoard.getRecommendation();
            }
            return null;
        }
    }

    @Transactional
    @Override
    public Long downRecommendation(Long boardId, RecommendationRequestForm recommendationRequestForm) {
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findById(boardId);
        if (maybeFreeBoard.isEmpty()) {
            return null;
        }
        FreeBoard freeBoard = maybeFreeBoard.get();

        Optional<Member> maybeMember = memberRepository.findById(recommendationRequestForm.getMemberId());
        if (maybeMember.isEmpty()) {
            return null;
        }
        Member member = maybeMember.get();

        Recommendation findRecommendation = recommendationRepository.findByFreeBoardAndMemberId(freeBoard, member.getId());
        if (findRecommendation == null) {
            Recommendation recommendation = new Recommendation(freeBoard, member);
            recommendation.decUnRecommendation();
            recommendation.setDecRecommendationStatus(true);
            recommendationRepository.save(recommendation);
            return freeBoard.getUnRecommendation();
        } else {
            if (findRecommendation.isDecRecommendationStatus()) {
                Recommendation recommendation = recommendationRepository.findByFreeBoardAndMemberId(freeBoard, member.getId());
                recommendation.incUnRecommendation();
                recommendationRepository.delete(recommendation);
                return freeBoard.getUnRecommendation();
            }
            return null;
        }
    }

    @Override
    public List<FreeBoardImageResourceResponse> findFreeBoardImage(Long boardId) {
        List<FreeBoardImageResource> freeBoardImageResourceList = freeBoardImageResourceRepository.findImagePathByFreeBoardId(boardId);
        List<FreeBoardImageResourceResponse> freeBoardImageResourceResponseList = new ArrayList<>();

        for (FreeBoardImageResource freeBoardImageResource : freeBoardImageResourceList) {
            System.out.println("imageResource path: " + freeBoardImageResource.getFreeBoardImageResourcePath());

            freeBoardImageResourceResponseList.add(new FreeBoardImageResourceResponse(
                    freeBoardImageResource.getFreeBoardImageResourcePath()));
        }

        return freeBoardImageResourceResponseList;
    }

    @Transactional
    @Override
    public List<FreeBoardResponse> bestFreeBoardList() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("recommendation").descending());
        //pageable 내장객체를 사용해서 index 0 부터 3개를 recommendation 으로 내림차
        Page<FreeBoard> freeBoardPage = freeBoardRepository.findAll(pageable);
        List<FreeBoard> freeBoards = freeBoardPage.getContent();

        List<FreeBoardResponse> freeBoardResponses = new ArrayList<>();

        for (FreeBoard freeBoard : freeBoards) {
            if (freeBoard.getRecommendation() >= 10) { // 조건 추가
                Long commentCount = (long) freeBoard.getFreeBoardCommentList().size();
                FreeBoardResponse freeBoardResponse = new FreeBoardResponse(
                        freeBoard.getBoardId(), freeBoard.getTitle(), freeBoard.getMember().getNickName(),
                        freeBoard.getContent(), freeBoard.getRegDate(), freeBoard.getUpdDate(), freeBoard.getMember().getId(),
                        freeBoard.getViewCnt(), freeBoard.getRecommendation(), freeBoard.getUnRecommendation(), commentCount
                );
                freeBoardResponses.add(freeBoardResponse);
                //response에 담아서 return
            }
        }
        return freeBoardResponses;
    }
}
