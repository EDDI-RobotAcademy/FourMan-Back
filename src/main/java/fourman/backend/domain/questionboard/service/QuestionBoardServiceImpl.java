package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.questionboard.controller.requestForm.QuestionBoardRequestForm;
import fourman.backend.domain.questionboard.entity.Comment;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.repository.CommentRepository;
import fourman.backend.domain.questionboard.repository.QuestionBoardRepository;
import fourman.backend.domain.questionboard.service.response.QuestionBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionBoardServiceImpl implements QuestionBoardService {

    @Autowired
    final private QuestionBoardRepository questionBoardRepository;

    @Autowired
    final private CommentRepository commentRepository;
    @Autowired
    final private MemberRepository memberRepository;


    @Transactional
    @Override
    public List<QuestionBoardResponse> list() {
        List<QuestionBoard> questionBoardList = questionBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
        List<QuestionBoardResponse> questionBoardResponseList = new ArrayList<>();

        for(QuestionBoard questionBoard: questionBoardList) {
            Long commentCount = (long) questionBoard.getQuestionBoardCommentList().size();
            QuestionBoardResponse questionBoardResponse = new QuestionBoardResponse(
                   questionBoard.getMember().getId(), questionBoard.getBoardId(), questionBoard.getTitle(),
                    questionBoard.getWriter(), questionBoard.getQuestionType(), questionBoard.getContent(),
                    questionBoard.getRegDate(), questionBoard.getUpdDate(), questionBoard.isSecret(), questionBoard.getViewCnt(), commentCount);
            questionBoardResponseList.add(questionBoardResponse);
        }
            return questionBoardResponseList;
        }


    @Override
    public QuestionBoard register(QuestionBoardRequestForm questionBoardRequestForm) {

        QuestionBoard questionBoard = new QuestionBoard();
        questionBoard.setTitle(questionBoardRequestForm.getTitle());
        questionBoard.setQuestionType(questionBoardRequestForm.getQuestionType());
        questionBoard.setContent(questionBoardRequestForm.getContent());
        questionBoard.setWriter(questionBoardRequestForm.getWriter());
        questionBoard.setSecret(questionBoardRequestForm.isSecret());

        Optional<Member> maybeMember = memberRepository.findById(questionBoardRequestForm.getMemberId());
        if(maybeMember.isEmpty()) {
            return null;
        }
        questionBoard.setMember(maybeMember.get());

        //게시글 생성 시 viewCnt 0으로 설정
        questionBoard.setViewCnt(0L);
        questionBoardRepository.save(questionBoard);
        return questionBoard;
    }

    @Override
    public QuestionBoardResponse read(Long boardId) {
        Optional<QuestionBoard> maybeQuestionBoard = questionBoardRepository.findById(boardId);

        if (maybeQuestionBoard.isEmpty()) {

            System.out.println("읽을 수 없음");
            return null;
        }
        //viewCnt를 증가시켜서 저장후 QuestionBoard를 반환함
        QuestionBoard questionBoard = maybeQuestionBoard.get();
        questionBoard.increaseViewCnt();
        questionBoardRepository.save(questionBoard);

        QuestionBoardResponse questionBoardResponse = new QuestionBoardResponse(
                questionBoard.getMember().getId(), questionBoard.getBoardId(), questionBoard.getTitle(),
                questionBoard.getWriter(), questionBoard.getQuestionType(), questionBoard.getContent(),
                questionBoard.getRegDate(), questionBoard.getUpdDate(), questionBoard.isSecret(), questionBoard.getViewCnt(), 0L);

        return questionBoardResponse;
        //처리 로직


    }

    @Override
    public QuestionBoard modify(Long boardId, QuestionBoardRequestForm questionBoardRequestForm) {
        Optional<QuestionBoard> maybeQuestionBoard = questionBoardRepository.findById(boardId);
        if(maybeQuestionBoard.isEmpty()) {
            return null;
        }
         QuestionBoard questionBoard = maybeQuestionBoard.get();
        questionBoard.setTitle(questionBoardRequestForm.getTitle());
        questionBoard.setContent(questionBoardRequestForm.getContent());
        questionBoardRepository.save(questionBoard);
        return questionBoard;
    }

    @Override
    public void delete(Long boardId) {
//        QuestionBoard, Comment 양방향 매핑 해서 추가 작업 필요 없이 바로 삭제되게끔 처리 (cascade.REMOVE)
//        List<Comment> commentList = commentRepository.findCommentByBoardId(boardId);
//
//        //댓글 먼저 삭제
//        for(Comment comment : commentList) {
//            commentRepository.delete(comment);
//        }
        questionBoardRepository.deleteById(boardId);
    }

    @Override
    public List<QuestionBoard> searchBoardList(String searchText) {
        return questionBoardRepository.findQuestionBoardBySearchText(searchText);

    }

    @Override
    public List<QuestionBoard> myQuestionBoardList(Long memberId) {
        return questionBoardRepository.findMyQuestionBoardByMemberId(memberId);
    }

//    @Override
//    public Long showViewCnt(Long boardId) {
//        Optional<QuestionBoard> maybeQuestionBoard = questionBoardRepository.findById(boardId);
//        if(maybeQuestionBoard.isEmpty()) {
//            return null;
//        }
//        QuestionBoard questionBoard = maybeQuestionBoard.get();
//        questionBoard.increaseViewCnt();
//        questionBoardRepository.save(questionBoard);
//        return questionBoard.getViewCnt();





}
