package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.Comment;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.repository.CommentRepository;
import fourman.backend.domain.questionboard.repository.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionBoardServiceImpl implements QuestionBoardService {

    @Autowired
    final private QuestionBoardRepository questionBoardRepository;

    @Autowired
    final private CommentRepository commentRepository;


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
        questionBoard.setMemberId(questionBoardRequestForm.getMemberId());

        questionBoardRepository.save(questionBoard);
        return questionBoard;
    }

    @Override
    public QuestionBoard read(Long boardId) {
        Optional<QuestionBoard> maybeQuestionBoard = questionBoardRepository.findById(boardId);

        if (maybeQuestionBoard.isEmpty()) {

            System.out.println("읽을 수 없음");
            return null;
        }
            return maybeQuestionBoard.get();
        //처리 로직


    }

    @Override
    public QuestionBoard modify(Long boardId, QuestionBoardRequestForm questionBoardRequestForm) {
        Optional<QuestionBoard> maybeQuestionBoard = questionBoardRepository.findById(boardId);
        if(maybeQuestionBoard.isEmpty()) {
            return null;
        }
         QuestionBoard questionBoard = maybeQuestionBoard.get();
        questionBoard.setTitle(questionBoardRequestForm.getTitle());
        questionBoard.setContent(questionBoardRequestForm.getContent());
        questionBoardRepository.save(questionBoard);
        return questionBoard;
    }

    @Override
    public void delete(Long boardId) {

        List<Comment> commentList = commentRepository.findCommentByBoardId(boardId);

        //댓글 먼저 삭제
        for(Comment comment : commentList) {
            commentRepository.delete(comment);
        }
        questionBoardRepository.deleteById(boardId);
    }

}
