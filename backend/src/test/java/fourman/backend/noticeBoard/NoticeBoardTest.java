package fourman.backend.noticeBoard;


import fourman.backend.domain.noticeBoard.controller.requestForm.NoticeBoardRequestForm;
import fourman.backend.domain.noticeBoard.entity.NoticeBoard;
import fourman.backend.domain.noticeBoard.service.NoticeBoardService;
import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NoticeBoardTest {

    @Autowired
    private NoticeBoardService noticeBoardService;

    @Test
    public void registerTest() {
        NoticeBoardRequestForm noticeBoardRequestForm = new NoticeBoardRequestForm("공지","공지사항","관리자","관리자만작성가능",1L);
        noticeBoardService.register(noticeBoardRequestForm);
    }

    @Test
    public void readTest() {
        NoticeBoard noticeBoard = noticeBoardService.read(1L);
        System.out.println(noticeBoard);
    }

    @Test
    public void modifyTest() {
        NoticeBoard noticeBoard = noticeBoardService.modify(3L,
                new NoticeBoardRequestForm("고오옹지","공지사항","고오오오옹지1","공지111111",1L));

        System.out.println(noticeBoard);
    }

    @Test
    public void removeTest() {
        noticeBoardService.delete(1L);
    }
}
