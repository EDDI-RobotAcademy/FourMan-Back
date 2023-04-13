package fourman.backend.domain.freeBoard.service;


import fourman.backend.domain.freeBoard.controller.requestForm.FreeBoardCommentRequestForm;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.entity.FreeBoardComment;
import fourman.backend.domain.freeBoard.repository.FreeBoardCommentRepository;
import fourman.backend.domain.freeBoard.repository.FreeBoardRepository;
import fourman.backend.domain.questionboard.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FreeBoardCommentServiceImpl implements FreeBoardCommentService{

    @Autowired
    private FreeBoardCommentRepository freeBoardCommentRepository;
    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Override
    public void register(FreeBoardCommentRequestForm freeBoardCommentRequestForm) {
        FreeBoard freeBoard = new FreeBoard();
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findById(freeBoardCommentRequestForm.getBoardId());

        if(maybeFreeBoard.isEmpty()) {

        }   freeBoard = maybeFreeBoard.get();

        FreeBoardComment freeBoardComment = new FreeBoardComment();

        freeBoardComment.setComment(freeBoardCommentRequestForm.getComment());
        freeBoardComment.setCommentWriter(freeBoardCommentRequestForm.getCommentWriter());
        freeBoardComment.setFreeBoard(freeBoard);
        freeBoardComment.setMemberId((freeBoardCommentRequestForm.getMemberId()));

        freeBoardCommentRepository.save(freeBoardComment);
    }

    @Override
    public List<FreeBoardComment> commentList(Long boardId) {
        return freeBoardCommentRepository.findFreeBoardCommentByBoardId(boardId);
    }

    @Override
    public void commentDelete(Long commentId) {
        freeBoardCommentRepository.deleteById(commentId);
    }

    @Override
    public FreeBoardComment commentModify(Long commentId, FreeBoardCommentRequestForm freeBoardCommentRequestForm) {
        Optional<FreeBoardComment> maybeComment = freeBoardCommentRepository.findById(commentId);
        if (maybeComment.isEmpty()) {
            return null;
        }
        FreeBoardComment freeBoardComment = maybeComment.get();
        freeBoardComment.setComment(freeBoardCommentRequestForm.getComment());
        freeBoardCommentRepository.save(freeBoardComment);
        return freeBoardComment;
    }
}



