package fourman.backend.domain.reviewBoard.service;

import fourman.backend.domain.reviewBoard.controller.requestForm.ReviewBoardRequestForm;
import fourman.backend.domain.reviewBoard.controller.responseForm.ReviewBoardImageResourceResponseForm;
import fourman.backend.domain.reviewBoard.controller.responseForm.ReviewBoardReadResponseForm;
import fourman.backend.domain.reviewBoard.controller.responseForm.ReviewBoardResponseForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewBoardService {

    void register(List<MultipartFile> fileList,
                  ReviewBoardRequestForm reviewBoardRequest);

    List<ReviewBoardResponseForm> list();

    ReviewBoardReadResponseForm read(Long reviewBoardId);

    List<ReviewBoardImageResourceResponseForm> findReviewBoardImage(Long reviewBoardId);

    void remove(Long reviewBoardId);
}
