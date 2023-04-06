package fourman.backend.domain.noticeBoard.controller;


import fourman.backend.domain.noticeBoard.controller.requestForm.NoticeBoardRequestForm;
import fourman.backend.domain.noticeBoard.entity.NoticeBoard;
import fourman.backend.domain.noticeBoard.service.NoticeBoardService;
import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice-board")
@RequiredArgsConstructor
public class NoticeBoardController {

    final private NoticeBoardService noticeBoardService;

    @PostMapping("/register")
    public NoticeBoard questionBoardRegister(@RequestBody NoticeBoardRequestForm noticeBoardRequestForm) {
        return noticeBoardService.register(noticeBoardRequestForm);
    }
}
