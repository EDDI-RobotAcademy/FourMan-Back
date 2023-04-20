package fourman.backend.domain.freeBoard.service;

import fourman.backend.domain.freeBoard.controller.requestForm.FreeBoardCommentRequestForm;
import fourman.backend.domain.freeBoard.entity.FreeBoardComment;
import fourman.backend.domain.freeBoard.service.responseForm.FreeBoardCommentResponseForm;
import fourman.backend.domain.questionboard.entity.Comment;

import java.util.List;

public interface FreeBoardCommentService {
    void register(FreeBoardCommentRequestForm freeBoardCommentRequestForm);

    List<FreeBoardCommentResponseForm> commentList(Long boardId);

    void commentDelete(Long commentId);

    FreeBoardComment commentModify(Long commentId, FreeBoardCommentRequestForm freeBoardCommentRequestForm);
}
