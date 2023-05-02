package fourman.backend.domain.noticeBoard.service;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.noticeBoard.controller.requestForm.NoticeBoardRequestForm;
import fourman.backend.domain.noticeBoard.entity.NoticeBoard;
import fourman.backend.domain.noticeBoard.repository.NoticeBoardRepository;
import fourman.backend.domain.noticeBoard.service.response.NoticeBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService{

    final private NoticeBoardRepository noticeBoardRepository;
    final private MemberRepository memberRepository;

    @Override
    public NoticeBoard register(NoticeBoardRequestForm noticeBoardRequestForm) {
        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setTitle(noticeBoardRequestForm.getTitle());
        noticeBoard.setWriter(noticeBoardRequestForm.getWriter());
        noticeBoard.setContent(noticeBoardRequestForm.getContent());
        noticeBoard.setNotice(noticeBoardRequestForm.getNotice());
        noticeBoard.setViewCnt(0L);

        Optional<Member> maybeMember = memberRepository.findById(noticeBoardRequestForm.getMemberId());
        if(maybeMember.isEmpty()) {
            return null;
        }
        noticeBoard.setMember(maybeMember.get());

        noticeBoardRepository.save(noticeBoard);
        return noticeBoard;
    }

    @Override
    public List<NoticeBoardResponse> list() {
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
        List<NoticeBoardResponse> noticeBoardResponseList = new ArrayList<>();

        for(NoticeBoard noticeBoard: noticeBoardList) {
            NoticeBoardResponse noticeBoardResponse = new NoticeBoardResponse(
                    noticeBoard.getBoardId(), noticeBoard.getTitle(), noticeBoard.getWriter(), noticeBoard.getNotice(),
                    noticeBoard.getContent(), noticeBoard.getRegDate(), noticeBoard.getUpdDate(), noticeBoard.getMember().getId(),
                    noticeBoard.getViewCnt()
            );
            noticeBoardResponseList.add(noticeBoardResponse);
        }
        return noticeBoardResponseList;
    }

    @Transactional
    @Override
    public NoticeBoardResponse read(Long boardId, HttpServletResponse response , HttpServletRequest request) {
        Optional<NoticeBoard> maybeNoticeBoard = noticeBoardRepository.findById(boardId);

        if(maybeNoticeBoard.isEmpty()) {
            System.out.println("찾을 수 없음");
            return null;
        }
        NoticeBoard noticeBoard = maybeNoticeBoard.get();
        // 조회 수 중복 방지
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("noticeBoardView")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("["+ boardId.toString() +"]")) {
                noticeBoard.increaseViewCnt();
                oldCookie.setValue(oldCookie.getValue() + "_[" + boardId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            noticeBoard.increaseViewCnt();
            Cookie newCookie = new Cookie("noticeBoardView", "[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
            System.out.println(newCookie);
        }

        NoticeBoardResponse noticeBoardResponse = new NoticeBoardResponse(
                noticeBoard.getBoardId(), noticeBoard.getTitle(), noticeBoard.getWriter(), noticeBoard.getNotice(),
                noticeBoard.getContent(), noticeBoard.getRegDate(), noticeBoard.getUpdDate(), noticeBoard.getMember().getId(),
                noticeBoard.getViewCnt());

        return noticeBoardResponse;
        }

    @Override
    public NoticeBoard modify(Long boardId, NoticeBoardRequestForm noticeBoardRequestForm) {
        Optional<NoticeBoard> maybeNoticeBoard = noticeBoardRepository.findById(boardId);
        if(maybeNoticeBoard.isEmpty()) {
            return null;
        }

        NoticeBoard noticeBoard = maybeNoticeBoard.get();

        noticeBoard.setContent(noticeBoardRequestForm.getContent());
        noticeBoard.setTitle(noticeBoardRequestForm.getTitle());

        noticeBoardRepository.save(noticeBoard);
        return noticeBoard;
    }

    @Override
    public void delete(Long boardId) {
        noticeBoardRepository.deleteById(boardId);
    }

}

