package fourman.backend.domain.reviewBoard.controller.responseForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewBoardImageResourceResponseForm {

    // 이미지 경로를 응답하기 위한 객체
    private String reviewBoardImageResourcePath;

}
