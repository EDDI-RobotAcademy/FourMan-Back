package fourman.backend.reviewBoard;

import fourman.backend.domain.reviewBoard.controller.requestForm.ReviewBoardRequestForm;
import fourman.backend.domain.reviewBoard.service.ReviewBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SpringBootTest
public class ReviewBoardTest {

    @Autowired
    private ReviewBoardService reviewBoardService;

//    @Test
//    public void 게시물_저장_테스트() {
//        ReviewBoardRequestForm reviewBoardRequestForm =
//                new ReviewBoardRequestForm("카페이름", "제목", "작성자", "내용", 3L, 1L);
//
//        List<MultipartFile> fileList = null;
//        reviewBoardService.register(fileList, reviewBoardRequestForm);
//    }
//
//    @Test void 게시물_리스트_조회_테스트() {
//        System.out.println(reviewBoardService.list());
//    }
//
//    @Test void 게시믈_삭제_테스트() {
//        reviewBoardService.remove(5L);
//        System.out.println(reviewBoardService.list());
//    }
//
//    @Test void 게시물_수정_테스트() {
//        ReviewBoardRequestForm reviewBoardRequestForm = new ReviewBoardRequestForm("카페이름", "제목", "작성자", "내용", 3L, 1L);
//
//        reviewBoardService.modify(113L, reviewBoardRequestForm);
//    }
//
//    @Test
//    public void 카페이름으로_별점_불러오기() {
//
//        System.out.println(reviewBoardService.Rating("명진카페"));
//    }

}
