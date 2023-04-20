package fourman.backend.domain.noticeBoard.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NoticeBoardResponse {

    private Long boardId;
    private String title;
    private String writer;
    private String notice;
    private String content;
    private Date regDate;
    private Date updDate;
    private Long memberId;
    private Long viewCnt;
}
