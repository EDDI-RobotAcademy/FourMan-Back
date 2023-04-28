package fourman.backend.domain.questionboard.controller;


import fourman.backend.domain.aop.aspect.SecurityAnnotations;
import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.service.QuestionBoardService;
import fourman.backend.domain.questionboard.service.response.QuestionBoardResponse;
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


//    @GetMapping("/list")
//    public List<QuestionBoardResponse> questionBoardList() {
//        return questionBoardService.list();
//    }

    @GetMapping("/all-list")
    public List<QuestionBoardResponse> questionBoardAllList() {
        return questionBoardService.listWithReplies();
    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @PostMapping("/register")
    public QuestionBoard questionBoardRegister(@RequestBody QuestionBoardRequestForm questionBoardRequestForm) {
        return questionBoardService.register(questionBoardRequestForm);
    }

    @GetMapping("/{boardId}") //boardId 받기
    public QuestionBoardResponse questionBoardRead(@PathVariable("boardId") Long boardId) {
        return questionBoardService.read(boardId);
    }
    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
    @PutMapping("/{boardId}")
    public QuestionBoard questionBoardModify(@PathVariable("boardId") Long boardId,
                                             @RequestBody QuestionBoardRequestForm questionBoardRequestForm) {
        return questionBoardService.modify(boardId, questionBoardRequestForm);
    }
    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.AUTHENTICATED)
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

    //read메서드와 합쳐서 따로 viewCnt를 따로 보내는게아닌 read에서 한번에 보냄
//    @GetMapping("/view/{boardId}")
//    public Long showViewCnt(@PathVariable("boardId") Long boardId) {
//       return questionBoardService.showViewCnt(boardId);
//    }

    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.MANAGER)
    @PostMapping("/register-reply")
    public QuestionBoard questionBoardReplyRegister(@RequestBody QuestionBoardRequestForm questionBoardRequestForm) {
        System.out.println("secret 체크하자 : " +questionBoardRequestForm.isSecret());
        System.out.println("parentBoardId도 체크하자 : " + questionBoardRequestForm.getParentBoardId());
        System.out.println("title 체크 :" + questionBoardRequestForm.getTitle());
        return questionBoardService.replyRegister(questionBoardRequestForm);
    }
}
