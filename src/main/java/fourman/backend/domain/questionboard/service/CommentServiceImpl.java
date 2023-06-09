package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.questionboard.controller.requestForm.CommentRequestForm;
import fourman.backend.domain.questionboard.entity.Comment;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.repository.CommentRepository;
import fourman.backend.domain.questionboard.repository.QuestionBoardRepository;
import fourman.backend.domain.questionboard.service.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{


    @Autowired
    CommentRepository commentRepository;
    @Autowired
    QuestionBoardRepository questionBoardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public void register(CommentRequestForm commentRequestForm) {
        QuestionBoard questionBoard = new QuestionBoard();
        Optional<QuestionBoard> maybeQuestionBoard = questionBoardRepository.findById(commentRequestForm.getBoardId());
        if(maybeQuestionBoard.isEmpty()) {

        } questionBoard = maybeQuestionBoard.get();


        Comment comment = new Comment();

        comment.setComment(commentRequestForm.getComment());
        comment.setCommentWriter(commentRequestForm.getComment());
        comment.setQuestionBoard(questionBoard);
        comment.setCommentWriter(commentRequestForm.getCommentWriter());

        Optional<Member> maybeMember = memberRepository.findById(commentRequestForm.getMemberId());

        if(maybeMember.isEmpty()) {
            log.info("없당");
        }

        comment.setMember(maybeMember.get());
        commentRepository.save(comment);

    }

    @Override
    public List<CommentResponse> commentList(Long boardId) {
        List<Comment> commentList = commentRepository.findCommentByBoardId(boardId);
        List<CommentResponse> commentResponseList = new ArrayList<>();

        for(Comment comment: commentList) {
            CommentResponse commentResponse = new CommentResponse(
                    comment.getCommentId(), comment.getComment(), comment.getCommentWriter(), comment.getRegDate(),
                    comment.getUdpDate(), comment.getQuestionBoard().getBoardId(), comment.getMember().getId()
            );
            commentResponseList.add(commentResponse);
        }
            return commentResponseList;

    }

    @Override
    public void commentDelete(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment commentModify(Long commentId, CommentRequestForm commentRequestForm) {
        Optional<Comment> maybeComment = commentRepository.findById(commentId);
        if (maybeComment.isEmpty()) {
            return null;
        }
        Comment comment = maybeComment.get();
        comment.setComment(commentRequestForm.getComment());
        commentRepository.save(comment);
        return comment;
    }


}




