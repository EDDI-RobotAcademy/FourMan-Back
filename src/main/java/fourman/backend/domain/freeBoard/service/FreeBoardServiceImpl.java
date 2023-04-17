package fourman.backend.domain.freeBoard.service;

import fourman.backend.domain.freeBoard.controller.requestForm.FreeBoardRequestForm;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.entity.FreeBoardComment;
import fourman.backend.domain.freeBoard.repository.FreeBoardCommentRepository;
import fourman.backend.domain.freeBoard.repository.FreeBoardRepository;
import fourman.backend.domain.questionboard.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService{

    final private FreeBoardRepository freeBoardRepository;

    final private FreeBoardCommentRepository freeBoardCommentRepository;

    @Override
    public FreeBoard register(FreeBoardRequestForm freeBoardRequest) {
        FreeBoard freeBoard = new FreeBoard();
        freeBoard.setTitle(freeBoardRequest.getTitle());
        freeBoard.setWriter(freeBoardRequest.getWriter());
        freeBoard.setContent(freeBoardRequest.getContent());
        freeBoard.setMemberId(freeBoardRequest.getMemberId());
        freeBoardRepository.save(freeBoard);

        return freeBoard;
    }

    @Override
    public List<FreeBoard> list() {
        return freeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
    }

    @Override
    public FreeBoard read(Long boardId) {
        // 일 수도 있고 아닐 수도 있고
        Optional<FreeBoard> maybeBoard = freeBoardRepository.findById(boardId);
        if (maybeBoard.isEmpty()) {
            log.info("읽을 수가 없드아!");
            return null;
        }
        return maybeBoard.get();
    }

    @Override
    public void remove(Long boardId) {
        List<FreeBoardComment> commentList = freeBoardCommentRepository.findFreeBoardCommentByBoardId(boardId);

        //댓글 먼저 삭제
        for(FreeBoardComment freeBoardComment : commentList) {
            freeBoardCommentRepository.delete(freeBoardComment);
        }

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


}
