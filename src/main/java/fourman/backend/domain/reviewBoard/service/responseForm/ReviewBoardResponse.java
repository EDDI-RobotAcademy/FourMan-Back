package fourman.backend.domain.reviewBoard.service.responseForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class ReviewBoardResponse {

    final private Long reviewBoardId;
    final private String cafeName;
    final private String title;
    final private String writer;
    final private String content;
    final private Long rating;
    final private Long memberId;
    final private Date regDate;
    final private Date updDate;
    final private String firstPhoto;

}
