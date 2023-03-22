package fourman.backend.domain.questionboard.controller.form;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuestionBoardRequest {

    private String title;
    private String questionType;
    private String writer;
    private String content;
}
