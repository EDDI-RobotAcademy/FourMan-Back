package fourman.backend.domain.reviewBoard.controller.responseForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
public class ReviewBoardReadResponseForm {

    // 상품 상세 정보를 응답하기 위한 객체

    private Long reviewBoardId;
    private String cafeName;
    private String Title;
    private String writer;
    private String content;
    private Date regDate;
    private Long memberId;
    private Long rating;

}
