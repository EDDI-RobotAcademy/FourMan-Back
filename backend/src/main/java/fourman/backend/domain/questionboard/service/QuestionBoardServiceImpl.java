package fourman.backend.domain.questionboard.service;

import fourman.backend.domain.questionboard.entity.QuestionBoard;
import fourman.backend.domain.questionboard.repository.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionBoardServiceImpl implements QuestionBoardService {

    final private QuestionBoardRepository questionBoardRepository;

    @Override
    public List<QuestionBoard> list() {

        return questionBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
    }
}

