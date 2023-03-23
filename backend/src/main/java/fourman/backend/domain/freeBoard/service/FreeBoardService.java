package fourman.backend.domain.freeBoard.service;

import fourman.backend.domain.freeBoard.controller.requestForm.FreeBoardRequestForm;
import fourman.backend.domain.freeBoard.entity.FreeBoard;

import java.util.List;

public interface FreeBoardService {
    public FreeBoard register(FreeBoardRequestForm freeBoardRequest);

    List<FreeBoard> list();

    FreeBoard read(Long boardId);

    void remove(Long boardId);
}
