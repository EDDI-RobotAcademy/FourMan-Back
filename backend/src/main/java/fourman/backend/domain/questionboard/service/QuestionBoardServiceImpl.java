package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.repository.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionBoardServiceImpl implements QuestionBoardService {

    final private QuestionBoardRepository questionBoardRepository;

    @Override
    public List<QuestionBoard> list() {
        return questionBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
    }

    @Override
    public QuestionBoard register(QuestionBoardRequestForm questionBoardRequestForm) {

        QuestionBoard questionBoard = new QuestionBoard();
        questionBoard.setTitle(questionBoardRequestForm.getTitle());
        questionBoard.setQuestionType(questionBoardRequestForm.getQuestionType());
        questionBoard.setContent(questionBoardRequestForm.getContent());
        questionBoard.setWriter(questionBoardRequestForm.getWriter());

        questionBoardRepository.save(questionBoard);
        return questionBoard;
    }

    @Override
    public QuestionBoard read(Long boardId) {
        Optional<QuestionBoard> maybeQuestionBoard = questionBoardRepository.findById(boardId);

        if (maybeQuestionBoard.isPresent()) {
            return maybeQuestionBoard.get();
        }

        return null;


    }

}
