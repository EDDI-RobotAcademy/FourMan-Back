package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.questionboard.controller.requestForm.CommentRequestForm;
import fourman.backend.domain.questionboard.entity.Comment;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.repository.CommentRepository;
import fourman.backend.domain.questionboard.repository.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{


    @Autowired
    CommentRepository commentRepository;
    @Autowired
    QuestionBoardRepository questionBoardRepository;

    @Override
    public void register(CommentRequestForm commentRequestForm) {
        QuestionBoard questionBoard =new QuestionBoard();
        Optional<QuestionBoard> maybeQuestionBoard = questionBoardRepository.findById(commentRequestForm.getBoardId());
        QuestionBoard board = maybeQuestionBoard.get();


        Comment comment = new Comment();

        comment.setComment(commentRequestForm.getComment());
        comment.setCommentWriter(commentRequestForm.getComment());
        comment.setQuestionBoard((board));


        commentRepository.save(comment);

    }
}
