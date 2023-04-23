package fourman.backend.domain.freeBoard.service.responseForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;


@Getter
@ToString
@AllArgsConstructor
public class FreeBoardResponse {

    final private Long boardId;
    final private String title;
    final private String writer;
    final private String content;
    final private Date regDate;
    final private Date updDate;
    final private Long memberId;
    final private Long viewCnt;
    final private Long recommendation;
}
