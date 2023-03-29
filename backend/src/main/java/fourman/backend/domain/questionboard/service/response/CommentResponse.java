package fourman.backend.domain.questionboard.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentResponse {

    final private String comment;
    final private String commentWriter;



}
