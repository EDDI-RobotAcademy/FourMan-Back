package fourman.backend.domain.reviewBoard.controller.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewBoardRequestForm {

    final private String cafeName;
    final private String title;
    final private String writer;
    final private String content;
    final private Long rating;
    final private Long memberId;

}
