package fourman.backend.domain.freeBoard.service;

import fourman.backend.domain.freeBoard.controller.requestForm.FreeBoardRequestForm;
import fourman.backend.domain.freeBoard.controller.requestForm.RecommendationRequestForm;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.service.responseForm.FreeBoardImageResourceResponse;
import fourman.backend.domain.freeBoard.service.responseForm.FreeBoardReadResponse;
import fourman.backend.domain.freeBoard.service.responseForm.FreeBoardResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FreeBoardService {
    public FreeBoard register(List<MultipartFile> fileList,
                              FreeBoardRequestForm freeBoardRequest);

    List<FreeBoardResponse> list();

    FreeBoardReadResponse read(Long boardId, Long memberId, HttpServletRequest request, HttpServletResponse response);

    void remove(Long boardId);

    Boolean modify(Long boardId, FreeBoardRequestForm boardRequest);

    List<FreeBoardResponse> myPageList(Long memberId);

    List<FreeBoard> searchFreeBoardList(String searchText);

//    Long incRecommendation(Long boardId);

//    Long decRecommendation(Long boardId);

    List<FreeBoardImageResourceResponse> findFreeBoardImage(Long boardId);

    Long upRecommendation(Long boardId, RecommendationRequestForm recommendationRequestForm);

    Long downRecommendation(Long boardId, RecommendationRequestForm recommendationRequestForm);

    List<FreeBoardResponse> bestFreeBoardList();
}
