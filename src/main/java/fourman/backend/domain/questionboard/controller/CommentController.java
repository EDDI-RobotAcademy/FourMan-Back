package fourman.backend.domain.questionboard.controller;


import fourman.backend.domain.questionboard.controller.requestForm.CommentRequestForm;
import fourman.backend.domain.questionboard.entity.Comment;
import fourman.backend.domain.questionboard.service.CommentService;
import fourman.backend.domain.questionboard.service.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/question-board/comment")
public class CommentController {

    final private CommentService commentService;

    @PostMapping("/register")
    public void commentRegister(@RequestBody CommentRequestForm commentRequestForm) {
        log.info("commentRequestForm");
        commentService.register(commentRequestForm);
    }

    @GetMapping("/{boardId}")
    public List<CommentResponse> commentList (@PathVariable("boardId") Long boardId) {
        log.info("commentList");
        return commentService.commentList(boardId);
    }

    @DeleteMapping("/{commentId}")
        public void commentDelete(@PathVariable("commentId") Long commentId) {
            log.info("commentDelete()");
            commentService.commentDelete(commentId);
    }
    @PutMapping("/{commentId}")
    public Comment commentModify(@PathVariable("commentId") Long commentId,
                                 @RequestBody CommentRequestForm commentRequestForm) {
        return commentService.commentModify(commentId, commentRequestForm);
}
}
