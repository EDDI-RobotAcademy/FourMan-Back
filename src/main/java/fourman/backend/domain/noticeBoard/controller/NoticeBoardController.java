package fourman.backend.domain.noticeBoard.controller;


import fourman.backend.domain.noticeBoard.controller.requestForm.NoticeBoardRequestForm;
import fourman.backend.domain.noticeBoard.entity.NoticeBoard;
import fourman.backend.domain.noticeBoard.service.NoticeBoardService;
import fourman.backend.domain.noticeBoard.service.response.NoticeBoardResponse;
import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public List<NoticeBoardResponse> noticeBoardList() {
        return noticeBoardService.list();
    }

    @GetMapping("/{boardId}")
    public NoticeBoardResponse noticeBoardRead(@PathVariable("boardId") Long boardId
                                                , HttpServletResponse response , HttpServletRequest request) {
        return noticeBoardService.read(boardId,response,request);
    }

    @PutMapping("/{boardId}")
    public NoticeBoard noticeBoardModify(@PathVariable("boardId") Long boardId,
                                             @RequestBody NoticeBoardRequestForm noticeBoardRequestForm) {
        return noticeBoardService.modify(boardId, noticeBoardRequestForm);
    }

    @DeleteMapping("/{boardId}")
        public void noticeBoardDelete(@PathVariable("boardId") Long boardId) {
            noticeBoardService.delete(boardId);
        }
    }

