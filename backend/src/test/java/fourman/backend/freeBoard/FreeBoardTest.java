package fourman.backend.freeBoard;

import fourman.backend.domain.freeBoard.service.FreeBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FreeBoardTest {

    @Autowired
    private FreeBoardService freeBoardService;

    @Test
    public void 게시물_삭제_테스트() {
        freeBoardService.remove(6L);

        freeBoardService.read(6L);
    }
}
