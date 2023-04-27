package fourman.backend.domain.eventBoard.controller;

import fourman.backend.domain.aop.aspect.SecurityAnnotations;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroDetailResponse;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroListResponse;
import fourman.backend.domain.eventBoard.controller.requestForm.EventRequestForm;
import fourman.backend.domain.eventBoard.service.EventService;
import fourman.backend.domain.eventBoard.service.response.EventDetailResponse;
import fourman.backend.domain.eventBoard.service.response.EventListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @PostMapping(value = "/register",
            consumes = {  MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE }) // 이미지+텍스트 업로드하는 경우 value , consumes 정보(이미지타입, json타입) 추가
    public Long registerEvent(
            @RequestPart(value = "thumbnail") List<MultipartFile> thumbnail,
            @RequestPart(value = "info") EventRequestForm eventRequestForm) {

        log.info("이벤트등록 컨트롤러-리퀘스트내용: " + eventRequestForm);

        return eventService.registerEvent(thumbnail, eventRequestForm);
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
    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @PutMapping(value = "/modify/{eventId}",
            consumes = {  MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE }) // 이미지+텍스트 업로드하는 경우 value , consumes 정보(이미지타입, json타입) 추가
    public Long modifyEvent(
            @RequestPart(value = "thumbnail", required = false) List<MultipartFile> thumbnail,
            @RequestPart(value = "info") EventRequestForm eventRequestForm,
            @PathVariable("eventId") Long eventId ) {

        log.info("이벤트 수정 컨트롤러-리퀘스트내용: " + eventRequestForm);

        return eventService.modifyEvent(eventId,thumbnail, eventRequestForm);
    }
    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        log.info("이벤트 삭제 컨트롤러");
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        log.info("uploadImage()");
        return eventService.uploadImage(file);
    }

    @GetMapping("/getCafe/{eventId}")
    public Cafe getCafeByEventId(@PathVariable("eventId") Long eventId) {
        log.info("getCafeByEventId");
        return eventService.getCafeByEventId(eventId);
    }
    @SecurityAnnotations.SecurityCheck(SecurityAnnotations.UserType.CAFE)
    @GetMapping(path = "/mylist/{cafeId}")
    public List<EventListResponse> eventListByCafeId(@PathVariable("cafeId") Long cafeId) {
        return eventService.eventListByCafeId(cafeId);
    }




}
