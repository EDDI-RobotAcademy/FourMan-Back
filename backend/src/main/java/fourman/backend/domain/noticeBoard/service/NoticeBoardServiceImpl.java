package fourman.backend.domain.noticeBoard.service;

import fourman.backend.domain.noticeBoard.controller.requestForm.NoticeBoardRequestForm;
import fourman.backend.domain.noticeBoard.entity.NoticeBoard;
import fourman.backend.domain.noticeBoard.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService{

    final private NoticeBoardRepository noticeBoardRepository;

    @Override
    public NoticeBoard register(NoticeBoardRequestForm noticeBoardRequestForm) {
        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setTitle(noticeBoardRequestForm.getTitle());
        noticeBoard.setWriter(noticeBoardRequestForm.getWriter());
        noticeBoard.setContent(noticeBoardRequestForm.getContent());
        noticeBoard.setMemberId(noticeBoardRequestForm.getMemberId());
        noticeBoard.setNotice(noticeBoardRequestForm.getNotice());

        noticeBoardRepository.save(noticeBoard);
        return noticeBoard;
    }

    @Override
    public List<NoticeBoard> list() {
        return noticeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
    }

    @Override
    public NoticeBoard read(Long boardId) {
        Optional<NoticeBoard> maybeNoticeBoard = noticeBoardRepository.findById(boardId);

        if(maybeNoticeBoard.isEmpty()) {
            System.out.println("찾을 수 없음");
            return null;
        }
        return maybeNoticeBoard.get();
        }

    }

