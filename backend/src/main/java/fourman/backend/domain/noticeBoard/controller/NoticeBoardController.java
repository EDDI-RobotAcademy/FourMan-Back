package fourman.backend.domain.noticeBoard.controller;


import fourman.backend.domain.noticeBoard.controller.requestForm.NoticeBoardRequestForm;
import fourman.backend.domain.noticeBoard.entity.NoticeBoard;
import fourman.backend.domain.noticeBoard.service.NoticeBoardService;
import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice-board")
@RequiredArgsConstructor
public class NoticeBoardController {

    final private NoticeBoardService noticeBoardService;

    @PostMapping("/register")
    public NoticeBoard noticeBoardRegister(@RequestBody NoticeBoardRequestForm noticeBoardRequestForm) {
        return noticeBoardService.register(noticeBoardRequestForm);
    }

    @GetMapping("/list")
    public List<NoticeBoard> noticeBoardList() {
        return noticeBoardService.list();
    }

    @GetMapping("/{boardId}")
    public NoticeBoard noticeBoardRead(@PathVariable("boardId") Long boardId) {
        return noticeBoardService.read(boardId);
    }
}
