package fourman.backend.domain.freeBoard.service;

import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.repository.FreeBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService{

    final private FreeBoardRepository freeBoardRepository;

    @Override
    public List<FreeBoard> list() {
        return freeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
    }

}
