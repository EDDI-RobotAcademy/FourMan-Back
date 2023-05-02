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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

//
//    @Transactional
//    @Override
//    public List<QuestionBoardResponse> list() {
//        List<QuestionBoard> questionBoardList = questionBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
//        List<QuestionBoardResponse> questionBoardResponseList = new ArrayList<>();
//
//        for(QuestionBoard questionBoard: questionBoardList) {
//            Long commentCount = (long) questionBoard.getQuestionBoardCommentList().size();
//            QuestionBoardResponse questionBoardResponse = new QuestionBoardResponse(
//                   questionBoard.getMember().getId(), questionBoard.getBoardId(), questionBoard.getTitle(),
//                    questionBoard.getWriter(), questionBoard.getQuestionType(), questionBoard.getContent(),
//                    questionBoard.getRegDate(), questionBoard.getUpdDate(), questionBoard.isSecret(), questionBoard.getViewCnt(), commentCount);
//            questionBoardResponseList.add(questionBoardResponse);
//        }
//            return questionBoardResponseList;
//        }


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
        questionBoard.setDepth(0);
        questionBoardRepository.save(questionBoard);
        return questionBoard;
    }

    @Transactional
    @Override
    public QuestionBoardResponse read(Long boardId, HttpServletResponse response, HttpServletRequest request) {
        Optional<QuestionBoard> maybeQuestionBoard = questionBoardRepository.findById(boardId);

        if (maybeQuestionBoard.isEmpty()) {

            System.out.println("읽을 수 없음");
            return null;
        }
        QuestionBoard questionBoard = maybeQuestionBoard.get();

        // 조회 수 중복 방지
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("questionBoardView")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("["+ boardId.toString() +"]")) {
                questionBoard.increaseViewCnt();
                oldCookie.setValue(oldCookie.getValue() + "_[" + boardId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            questionBoard.increaseViewCnt();
            Cookie newCookie = new Cookie("questionBoardView", "[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
            System.out.println(newCookie);
        }


        QuestionBoardResponse questionBoardResponse = new QuestionBoardResponse(
                questionBoard.getMember().getId(), questionBoard.getBoardId(), questionBoard.getTitle(),
                questionBoard.getWriter(), questionBoard.getQuestionType(), questionBoard.getContent(),
                questionBoard.getRegDate(), questionBoard.getUpdDate(), questionBoard.isSecret(), questionBoard.getViewCnt(), 0L, null, questionBoard.getDepth());

        return questionBoardResponse;


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

    @Override
    public QuestionBoard replyRegister(QuestionBoardRequestForm questionBoardRequestForm) {
        Optional<Member> maybeMember = memberRepository.findById(questionBoardRequestForm.getMemberId());
        if(maybeMember.isEmpty()){
            return null;
        }
        Optional<QuestionBoard> maybeParentBoard = questionBoardRepository.findById(questionBoardRequestForm.getParentBoardId());
        if(maybeParentBoard.isEmpty()) {
            return null;
        }

        QuestionBoard parentBoard = maybeParentBoard.get();
        Member member = maybeMember.get();

        QuestionBoard questionBoard = new QuestionBoard();
        questionBoard.setTitle(questionBoardRequestForm.getTitle());
        questionBoard.setWriter(questionBoardRequestForm.getWriter());
        questionBoard.setQuestionType(questionBoardRequestForm.getQuestionType());
        questionBoard.setContent(questionBoardRequestForm.getContent());
        questionBoard.setParentBoard(parentBoard);
        questionBoard.setMember(member);
        questionBoard.setSecret(questionBoardRequestForm.isSecret());
        questionBoard.setViewCnt(0L);


        // 부모 게시물이 최상위 게시물인 경우, 자식 게시물의 depth 값을 1로 설정
        if (parentBoard.getDepth() == 0) {
            questionBoard.setDepth(1);
        } else {
            // 부모 게시물이 이미 자식 게시물인 경우, 부모 게시물의 depth 값을 가져와 +1 한 뒤 자식 게시물의 depth 값을 설정
            questionBoard.setDepth(parentBoard.getDepth() + 1);
        }

        //부모 게시글의 replyCnt 증가
        parentBoard.setReplyCnt(parentBoard.getReplyCnt() +1);
        questionBoardRepository.save(questionBoard);
        questionBoardRepository.save(parentBoard);
        return questionBoard;
    }

    //QuestionBoardResponse를 반환하는 부분에서 자식 게시물(replies) 정보를 포함해 반환
    private QuestionBoardResponse convertToQuestionBoardResponse(QuestionBoard questionBoard) {
        List<QuestionBoardResponse> replies = questionBoard.getReplies().stream()
                .map(this::convertToQuestionBoardResponse)
                .collect(Collectors.toList());

        Long commentCount = (long) questionBoard.getQuestionBoardCommentList().size();

        return new QuestionBoardResponse(
                questionBoard.getMember().getId(),
                questionBoard.getBoardId(),
                questionBoard.getTitle(),
                questionBoard.getWriter(),
                questionBoard.getQuestionType(),
                questionBoard.getContent(),
                questionBoard.getRegDate(),
                questionBoard.getUpdDate(),
                questionBoard.isSecret(),
                questionBoard.getViewCnt(),
                commentCount,
                replies,
                questionBoard.getDepth()
        );
    }

        @Transactional
        @Override
        public List<QuestionBoardResponse> listWithReplies() {
            List<QuestionBoard> questionBoardList = questionBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
            List<QuestionBoardResponse> questionBoardResponseList = new ArrayList<>();

            for (QuestionBoard questionBoard : questionBoardList) {
                if (questionBoard.getParentBoard() == null) { // only process parent boards
                    // 부모 게시물과 자식 게시물 정보를 포함한 QuestionBoardResponse 객체 생성
                    QuestionBoardResponse questionBoardResponse = convertToQuestionBoardResponse(questionBoard);
                    questionBoardResponseList.add(questionBoardResponse);
                }
            }
            return questionBoardResponseList;
        }

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





