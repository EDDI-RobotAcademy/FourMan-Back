package fourman.backend.domain.noticeBoard.service;

import fourman.backend.domain.noticeBoard.controller.requestForm.NoticeBoardRequestForm;
import fourman.backend.domain.noticeBoard.entity.NoticeBoard;

public interface NoticeBoardService {
    NoticeBoard register(NoticeBoardRequestForm noticeBoardRequestForm);
}
