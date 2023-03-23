package fourman.backend.domain.questionboard.controller.requestForm;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuestionBoardRequestForm {



    //받을 폼
    final private String title;
    final private String questionType;
    final private String writer;
    final private String content;
}
