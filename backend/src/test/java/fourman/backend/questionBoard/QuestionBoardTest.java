package fourman.backend.questionBoard;

import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.service.QuestionBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QuestionBoardTest {
    @Autowired
    private QuestionBoardService questionBoardService;

    @Test
    public void registerTest() {
        QuestionBoardRequestForm questionBoardRequestForm =
                new QuestionBoardRequestForm("1", "2", "3","4");

        questionBoardService.register(questionBoardRequestForm);
    }

    @Test
    public void readTest() {
      QuestionBoard questionBoard = questionBoardService.read(1L);
        System.out.println(questionBoard);
    }
}
