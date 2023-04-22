package fourman.backend.domain.eventBoard.controller;

import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroDetailResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroListResponse;
import fourman.backend.domain.eventBoard.controller.requestForm.EventRequestForm;
import fourman.backend.domain.eventBoard.service.EventService;
import fourman.backend.domain.eventBoard.service.response.EventDetailResponse;
import fourman.backend.domain.eventBoard.service.response.EventListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping(value = "/register",
            consumes = {  MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE }) // 이미지+텍스트 업로드하는 경우 value , consumes 정보(이미지타입, json타입) 추가
    public Long registerCafe(
            @RequestPart(value = "thumbnail") List<MultipartFile> thumbnail,
            @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList,
            @RequestPart(value = "info") EventRequestForm eventRequestForm) {

        log.info("이벤트등록 컨트롤러-파일리스트: " + fileList.toString());
        log.info("이벤트등록 컨트롤러-리퀘스트내용: " + eventRequestForm);

        return eventService.registerEvent(thumbnail, fileList, eventRequestForm);
    }
    @GetMapping(path = "/list")
    public List<EventListResponse> eventList() {
        return eventService.list();
    }



    @GetMapping("detail/{eventId}")
    public EventDetailResponse eventRead(@PathVariable("eventId") Long eventId) {
        log.info("eventRead()");
        return eventService.read(eventId);
    }



}
