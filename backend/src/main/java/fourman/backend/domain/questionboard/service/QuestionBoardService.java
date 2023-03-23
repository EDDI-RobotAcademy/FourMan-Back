package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;

import java.util.List;

public interface QuestionBoardService {

    List<QuestionBoard> list();

    public QuestionBoard register(QuestionBoardRequestForm questionBoardRequestForm);

    QuestionBoard read(Long boardId);
}
