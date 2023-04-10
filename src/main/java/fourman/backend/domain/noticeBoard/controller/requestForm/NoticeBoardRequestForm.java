package fourman.backend.domain.noticeBoard.controller.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoticeBoardRequestForm {

    final private String title;
    final private String notice;
    final private String writer;
    final private String content;
    final private Long memberId;
}
