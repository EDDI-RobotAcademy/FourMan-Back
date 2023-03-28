package fourman.backend.domain.questionboard.controller;


import fourman.backend.domain.questionboard.controller.requestForm.CommentRequestForm;
import fourman.backend.domain.questionboard.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/question_board/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/register")
    public void commentRegister(@RequestBody CommentRequestForm commentRequestForm) {
        log.info("commentRequestForm");
        commentService.register(commentRequestForm);
    }

}
