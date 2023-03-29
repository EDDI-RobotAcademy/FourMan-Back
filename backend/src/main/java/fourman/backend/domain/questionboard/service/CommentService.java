package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.questionboard.controller.requestForm.CommentRequestForm;
import fourman.backend.domain.questionboard.entity.Comment;
import fourman.backend.domain.questionboard.service.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentService {
    void register(CommentRequestForm commentRequestForm);

    List<Comment> commentList(Long boardId);
}
