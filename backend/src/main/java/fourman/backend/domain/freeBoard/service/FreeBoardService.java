package fourman.backend.domain.freeBoard.service;

import fourman.backend.domain.freeBoard.controller.request.FreeBoardRequest;
import fourman.backend.domain.freeBoard.entity.FreeBoard;

import java.util.List;

public interface FreeBoardService {
    public FreeBoard register(FreeBoardRequest freeBoardRequest);

    List<FreeBoard> list();
}
