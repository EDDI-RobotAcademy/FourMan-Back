package fourman.backend.domain.reviewBoard.service;

import fourman.backend.domain.reviewBoard.controller.requestForm.ReviewBoardRequestForm;
import fourman.backend.domain.reviewBoard.service.responseForm.ReviewBoardImageResourceResponse;
import fourman.backend.domain.reviewBoard.service.responseForm.ReviewBoardReadResponse;
import fourman.backend.domain.reviewBoard.service.responseForm.ReviewBoardResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewBoardService {

    void register(List<MultipartFile> fileList,
                  ReviewBoardRequestForm reviewBoardRequest);

    List<ReviewBoardResponse> list();

    ReviewBoardReadResponse read(Long reviewBoardId);

    List<ReviewBoardImageResourceResponse> findReviewBoardImage(Long reviewBoardId);

    void remove(Long reviewBoardId);

    Boolean modify(Long reivewBoardId, ReviewBoardRequestForm reviewBoardRequest);

    List<Long> Rating(String cafeName);

    List<ReviewBoardResponse> myPageList(Long memberId);
}
