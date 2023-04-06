package fourman.backend.noticeBoard;


import fourman.backend.domain.noticeBoard.controller.requestForm.NoticeBoardRequestForm;
import fourman.backend.domain.noticeBoard.service.NoticeBoardService;
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
}
