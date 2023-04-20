package fourman.backend.domain.noticeBoard.service;

import fourman.backend.domain.noticeBoard.controller.requestForm.NoticeBoardRequestForm;
import fourman.backend.domain.noticeBoard.entity.NoticeBoard;
import fourman.backend.domain.noticeBoard.service.response.NoticeBoardResponse;

import java.util.List;

public interface NoticeBoardService {
    NoticeBoard register(NoticeBoardRequestForm noticeBoardRequestForm);

    List<NoticeBoardResponse> list();

    NoticeBoardResponse read(Long boardId);

    NoticeBoard modify(Long boardId, NoticeBoardRequestForm noticeBoardRequestForm);

    void delete(Long boardId);
}
