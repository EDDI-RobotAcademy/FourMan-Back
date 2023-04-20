package fourman.backend.domain.freeBoard.service;

import fourman.backend.domain.freeBoard.controller.requestForm.FreeBoardRequestForm;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.entity.FreeBoardComment;
import fourman.backend.domain.freeBoard.repository.FreeBoardCommentRepository;
import fourman.backend.domain.freeBoard.repository.FreeBoardRepository;
import fourman.backend.domain.freeBoard.service.responseForm.FreeBoardResponseForm;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.questionboard.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService{

    final private FreeBoardRepository freeBoardRepository;

    final private MemberRepository memberRepository;

    @Override
    public FreeBoard register(FreeBoardRequestForm freeBoardRequest) {
        FreeBoard freeBoard = new FreeBoard();
        freeBoard.setTitle(freeBoardRequest.getTitle());
        freeBoard.setWriter(freeBoardRequest.getWriter());
        freeBoard.setContent(freeBoardRequest.getContent());

        Optional<Member> maybeMember = memberRepository.findById(freeBoardRequest.getMemberId());

        if(maybeMember.isEmpty()) {
            return null;
        }
        freeBoard.setMember(maybeMember.get());

        freeBoard.setViewCnt(0L);
        freeBoard.setRecommendation(0L);
        freeBoardRepository.save(freeBoard);

        return freeBoard;
    }

    @Override
    public List<FreeBoardResponseForm> list() {

        List<FreeBoard> freeBoardList = freeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
        List<FreeBoardResponseForm> freeBoardResponseFormList = new ArrayList<>();

        for (FreeBoard freeBoard: freeBoardList) {
            FreeBoardResponseForm freeBoardResponseForm = new FreeBoardResponseForm(
                    freeBoard.getBoardId(), freeBoard.getTitle(), freeBoard.getWriter(), freeBoard.getContent(),
                    freeBoard.getRegDate(), freeBoard.getUpdDate(), freeBoard.getMember().getId(), freeBoard.getViewCnt(), freeBoard.getRecommendation()
            );

            freeBoardResponseFormList.add(freeBoardResponseForm);
        }
        return freeBoardResponseFormList;
    }

    @Override
    public FreeBoardResponseForm read(Long boardId) {
        // 일 수도 있고 아닐 수도 있고
        Optional<FreeBoard> maybeBoard = freeBoardRepository.findById(boardId);
        if (maybeBoard.isEmpty()) {
            log.info("읽을 수가 없드아!");
            return null;
        }
        FreeBoard freeBoard = maybeBoard.get();
        freeBoard.increaseViewCnt();
        freeBoardRepository.save(freeBoard);

        FreeBoardResponseForm freeBoardResponseForm = new FreeBoardResponseForm(
                freeBoard.getBoardId(), freeBoard.getTitle(), freeBoard.getWriter(), freeBoard.getContent(),
                freeBoard.getRegDate(), freeBoard.getUpdDate(), freeBoard.getMember().getId(), freeBoard.getViewCnt(), freeBoard.getRecommendation()
        );
        return freeBoardResponseForm;
    }

    @Override
    public void remove(Long boardId) {

        freeBoardRepository.deleteById(boardId);
    }

    @Override
    public FreeBoard modify(Long boardId, FreeBoardRequestForm boardRequest) {
        Optional<FreeBoard> maybeBoard = freeBoardRepository.findById(boardId);
        if (maybeBoard.isEmpty()) {
            System.out.println("Board 정보를 찾지 못했습니다: " + boardId);
            return null;
        }
        FreeBoard freeBoard = maybeBoard.get();
        freeBoard.setTitle(boardRequest.getTitle());
        freeBoard.setContent(boardRequest.getContent());
        freeBoardRepository.save(freeBoard);
        return freeBoard;
    }

    @Override
    public List<FreeBoard> myPageList(Long memberId) {
        return freeBoardRepository.findFreeBoardByMemberId(memberId);
    }

    @Override
    public List<FreeBoard> searchFreeBoardList(String searchText) {
        return freeBoardRepository.findSearchFreeBoardBySearchText(searchText);
    }

    @Override
    public Long incRecommendation(Long boardId) {
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findById(boardId);
        if(maybeFreeBoard.isEmpty()) {
            return null;
        }
        FreeBoard freeBoard = maybeFreeBoard.get();
        freeBoard.increaseRecommendation();
        freeBoardRepository.save(freeBoard);
        return freeBoard.getRecommendation();
    }

    @Override
    public Long decRecommendation(Long boardId) {
        Optional<FreeBoard> maybeFreeboard = freeBoardRepository.findById(boardId);
        if(maybeFreeboard.isEmpty()) {
            return null;
        }
        FreeBoard freeBoard = maybeFreeboard.get();
        freeBoard.decreaseRecommendation();
        freeBoardRepository.save(freeBoard);
        return freeBoard.getRecommendation();
    }
}
