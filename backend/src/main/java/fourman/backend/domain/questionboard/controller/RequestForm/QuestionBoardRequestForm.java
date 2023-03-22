package fourman.backend.domain.questionboard.controller.RequestForm;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuestionBoardRequestForm {



    //받을 폼
    private String title;
    private String questionType;
    private String writer;
    private String content;
}
