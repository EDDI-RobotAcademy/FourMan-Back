package fourman.backend.domain.freeBoard.controller.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecommendationRequestForm {

    final private Long boardId;
    final private Long memberId;
}
