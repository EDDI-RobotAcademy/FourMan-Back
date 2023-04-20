package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.questionboard.controller.requestForm.CommentRequestForm;
import fourman.backend.domain.questionboard.entity.Comment;
import fourman.backend.domain.questionboard.service.response.CommentResponse;

import java.util.List;

public interface CommentService {
    void register(CommentRequestForm commentRequestForm);

    List<CommentResponse> commentList(Long boardId);

    void commentDelete(Long commentId);

    Comment commentModify(Long commentId, CommentRequestForm commentRequestForm);
}
