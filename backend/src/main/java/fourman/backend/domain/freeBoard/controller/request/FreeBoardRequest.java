package fourman.backend.domain.freeBoard.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FreeBoardRequest {

    final private String title;
    final private String writer;
    final private String content;

}
