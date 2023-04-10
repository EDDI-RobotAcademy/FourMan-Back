package fourman.backend.domain.questionboard.controller.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestForm {

    final private String comment;
    final private Long boardId;
    final private String commentWriter;
    final private Long memberId;
}
