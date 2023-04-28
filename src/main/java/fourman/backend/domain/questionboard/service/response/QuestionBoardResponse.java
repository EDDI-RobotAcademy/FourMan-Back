package fourman.backend.domain.questionboard.service.response;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class QuestionBoardResponse {

    final private Long memberId;
    final private Long boardId;
    final private String title;
    final private String writer;
    final private String questionType;
    final private String content;
    final private Date regDate;
    final private Date updDate;
    final private boolean secret;
    final private Long viewCnt;
    final private Long commentCount;
    final private List<QuestionBoardResponse> replies;
    final private int depth;
}
