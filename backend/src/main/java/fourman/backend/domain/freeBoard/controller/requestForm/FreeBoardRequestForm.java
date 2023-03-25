package fourman.backend.domain.freeBoard.controller.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FreeBoardRequestForm {

    final private String title;
    final private String writer;
    final private String content;
    final private Long memberId;

}
