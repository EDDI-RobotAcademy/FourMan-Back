package fourman.backend.domain.questionboard.controller;


import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.service.QuestionBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/question-board")
@RequiredArgsConstructor
public class QuestionBoardController {


    final private QuestionBoardService questionBoardService;


    @GetMapping("/list")
    public List<QuestionBoard> questionBoardList() {
        return questionBoardService.list();
    }

    @PostMapping("/register")
    public QuestionBoard questionBoardRegister(@RequestBody QuestionBoardRequestForm questionBoardRequestForm) {
        return questionBoardService.register(questionBoardRequestForm);
    }

    @GetMapping("/{boardId}") //boardId 받기
    public QuestionBoard questionBoardRead(@PathVariable("boardId") Long boardId) {
        return questionBoardService.read(boardId);
    }

    @PutMapping("/{boardId}")
    public QuestionBoard questionBoardModify(@PathVariable("boardId") Long boardId,
                                             @RequestBody QuestionBoardRequestForm questionBoardRequestForm) {
        return questionBoardService.modify(boardId, questionBoardRequestForm);
    }

    @DeleteMapping("/{boardId}")
    public void questionBoardDelete(@PathVariable("boardId") Long boardId) {
        questionBoardService.delete(boardId);
    }

    @GetMapping("/search/{searchText}")
    public List<QuestionBoard> searchBoardList(@PathVariable("searchText") String searchText) {
        return questionBoardService.searchBoardList(searchText);
    }

    @GetMapping("/myPage/{memberId}")
    public List<QuestionBoard> myQuestionBoardList(@PathVariable("memberId") Long memberId) {
        return questionBoardService.myQuestionBoardList(memberId);
    }

    @GetMapping("/view/{boardId}")
    public Long showViewCnt(@PathVariable("boardId") Long boardId) {
       return questionBoardService.showViewCnt(boardId);
    }
}
