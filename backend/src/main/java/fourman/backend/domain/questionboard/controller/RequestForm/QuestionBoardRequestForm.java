package fourman.backend.domain.questionboard.controller.RequestForm;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuestionBoardRequestForm {

    private String title;
    private String questionType;
    private String writer;
    private String content;
}
