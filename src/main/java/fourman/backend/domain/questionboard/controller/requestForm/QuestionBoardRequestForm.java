package fourman.backend.domain.questionboard.controller.requestForm;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RequiredArgsConstructor
public class QuestionBoardRequestForm {



    //받을 폼
    final private String title;
    final private Long parentBoardId;
    final private String questionType;
    final private String writer;
    final private String content;
    final private Long memberId;
    final private boolean secret;
    final private Long viewCnt;
}
