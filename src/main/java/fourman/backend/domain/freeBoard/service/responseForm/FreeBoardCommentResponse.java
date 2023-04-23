package fourman.backend.domain.freeBoard.service.responseForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;


@Getter
@ToString
@AllArgsConstructor
public class FreeBoardCommentResponse {

    final private Long commentId;
    final private String comment;
    final private String commentWriter;
    final private Date regDate;
    final private Date udpDate;
    final private Long memberId;
    final private Long boardId;

}
