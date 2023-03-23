package fourman.backend.domain.freeBoard.controller;

import fourman.backend.domain.freeBoard.controller.requestForm.FreeBoardRequestForm;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/free-board")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8887", allowedHeaders = "*")
public class FreeBoardController {

    final private FreeBoardService freeBoardService;

    @PostMapping("/register")
    public FreeBoard boardRegister (@RequestBody FreeBoardRequestForm boardRequest) {
        log.info("boardRegister()");

        return freeBoardService.register(boardRequest);
    }

    @GetMapping("/list")
    public List<FreeBoard> FreeBoardList () {

        return freeBoardService.list();
    }

    @GetMapping("/{boardId}")
    public FreeBoard boardRead(@PathVariable("boardId") Long boardId) {
        log.info("boardRead()");

        return freeBoardService.read(boardId);
    }

}
