package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.questionboard.controller.requestForm.CommentRequestForm;
import fourman.backend.domain.questionboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentService {
    void register(CommentRequestForm commentRequestForm);
}
