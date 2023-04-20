package fourman.backend.domain.questionboard.service.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class QuestionBoardResponse {

    private Long memberId;
    private Long boardId;
    private String title;
    private String writer;
    private String questionType;
    private String content;
    private Date regDate;
    private Date updDate;
    private boolean secret;
    private Long viewCnt;




}
