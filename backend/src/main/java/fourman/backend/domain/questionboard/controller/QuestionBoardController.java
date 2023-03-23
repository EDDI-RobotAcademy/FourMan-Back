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
@CrossOrigin(origins = "http://localhost:8887", allowedHeaders = "*")
public class QuestionBoardController {


    final private QuestionBoardService questionBoardService;

    @GetMapping("/list")
    public List<QuestionBoard> QuestionBoardList() {
       return questionBoardService.list();
    }

    @PostMapping("/register")
    public QuestionBoard QuestionBoardRegister(@RequestBody QuestionBoardRequestForm questionBoardRequestForm) {
        return questionBoardService.register(questionBoardRequestForm);
    }

    @GetMapping("/read")
    public QuestionBoard QuestionBoardRead(@PathVariable("boardId") Long boardId) {
        return questionBoardService.read(boardId);
    }

}
