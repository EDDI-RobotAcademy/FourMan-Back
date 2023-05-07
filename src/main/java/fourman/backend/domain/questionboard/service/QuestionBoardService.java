package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.service.response.QuestionBoardResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface QuestionBoardService {

//    List<QuestionBoardResponse> list();

    public QuestionBoard register(QuestionBoardRequestForm questionBoardRequestForm);

    QuestionBoardResponse read(Long boardId, HttpServletResponse response, HttpServletRequest request);

    QuestionBoard modify(Long boardId, QuestionBoardRequestForm questionBoardRequestForm);

    void delete(Long boardId);

    List<QuestionBoard> searchBoardList(String searchText);

    List<QuestionBoardResponse> myQuestionBoardList(Long memberId);

    QuestionBoard replyRegister(QuestionBoardRequestForm questionBoardRequestForm);

    List<QuestionBoardResponse> listWithReplies();


//    Long showViewCnt(Long boardId);
}
