package fourman.backend.domain.freeBoard.service;


import fourman.backend.domain.freeBoard.controller.requestForm.FreeBoardCommentRequestForm;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.entity.FreeBoardComment;
import fourman.backend.domain.freeBoard.repository.FreeBoardCommentRepository;
import fourman.backend.domain.freeBoard.repository.FreeBoardRepository;
import fourman.backend.domain.freeBoard.service.responseForm.FreeBoardCommentResponse;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    final private MemberRepository memberRepository;

    @Override
    public void register(FreeBoardCommentRequestForm freeBoardCommentRequestForm) {
        FreeBoard freeBoard = new FreeBoard();
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findById(freeBoardCommentRequestForm.getBoardId());

        if(maybeFreeBoard.isEmpty()) {

        }   freeBoard = maybeFreeBoard.get();

        FreeBoardComment freeBoardComment = new FreeBoardComment();

        freeBoardComment.setComment(freeBoardCommentRequestForm.getComment());
        freeBoardComment.setFreeBoard(freeBoard);

        Optional<Member> maybeMember = memberRepository.findById(freeBoardCommentRequestForm.getMemberId());

        if(maybeMember.isEmpty()) {

        }
        freeBoardComment.setMember(maybeMember.get());

        freeBoardCommentRepository.save(freeBoardComment);
    }

    @Transactional
    @Override
    public List<FreeBoardCommentResponse> commentList(Long boardId) {

        List<FreeBoardComment> freeBoardCommentList = freeBoardCommentRepository.findFreeBoardCommentByBoardId(boardId);
        List<FreeBoardCommentResponse> freeBoardCommentResponseList = new ArrayList<>();

        for (FreeBoardComment freeBoardComment: freeBoardCommentList) {
            FreeBoardCommentResponse freeBoardCommentResponse = new FreeBoardCommentResponse(
                    freeBoardComment.getCommentId(), freeBoardComment.getComment(), freeBoardComment.getMember().getNickName(), freeBoardComment.getRegDate(),
                    freeBoardComment.getUdpDate(), freeBoardComment.getMember().getId(), freeBoardComment.getFreeBoard().getBoardId()
            );

            freeBoardCommentResponseList.add(freeBoardCommentResponse);
        }

        return freeBoardCommentResponseList;
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



