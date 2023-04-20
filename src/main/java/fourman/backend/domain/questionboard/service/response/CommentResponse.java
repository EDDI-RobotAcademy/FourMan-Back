package fourman.backend.domain.questionboard.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String comment;
    private String commentWriter;
    private Date regDate;
    private Date udpDate;
    private Long boardId;
    private Long memberId;
}
