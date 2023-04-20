package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.service.response.QuestionBoardResponse;

import java.util.List;

public interface QuestionBoardService {

    List<QuestionBoardResponse> list();

    public QuestionBoard register(QuestionBoardRequestForm questionBoardRequestForm);

    QuestionBoardResponse read(Long boardId);

    QuestionBoard modify(Long boardId, QuestionBoardRequestForm questionBoardRequestForm);

    void delete(Long boardId);

    List<QuestionBoard> searchBoardList(String searchText);

    List<QuestionBoard> myQuestionBoardList(Long memberId);


//    Long showViewCnt(Long boardId);
}
