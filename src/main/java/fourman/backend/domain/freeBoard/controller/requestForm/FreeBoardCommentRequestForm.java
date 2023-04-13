package fourman.backend.domain.freeBoard.controller.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FreeBoardCommentRequestForm {

    final private String comment;
    final private Long boardId;
    final private String commentWriter;
    final private Long memberId;
}
