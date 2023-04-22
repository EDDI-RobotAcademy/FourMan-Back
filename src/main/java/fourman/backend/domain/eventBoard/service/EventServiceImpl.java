package fourman.backend.domain.eventBoard.service;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.service.response.CafeIntroDetailResponse;
import fourman.backend.domain.eventBoard.controller.requestForm.EventRequestForm;
import fourman.backend.domain.eventBoard.entity.Event;
import fourman.backend.domain.eventBoard.entity.EventBoardImageResource;
import fourman.backend.domain.eventBoard.repository.EventBoardImageResourceRepository;
import fourman.backend.domain.eventBoard.repository.EventRepository;
import fourman.backend.domain.eventBoard.service.response.EventDetailResponse;
import fourman.backend.domain.eventBoard.service.response.EventListResponse;
import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.repository.CafeCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventBoardImageResourceRepository eventBoardImageResourceRepository;
    private final CafeCodeRepository cafeCodeRepository;
    //
    @Override
    public Long registerEvent(List<MultipartFile> thumbnail, List<MultipartFile> fileList, EventRequestForm eventRequestForm) {
        // 1. event 저장
        Event event = new Event();
        event.setEventName(eventRequestForm.getEventName());
        event.setEventStartDate(eventRequestForm.getEventStartDate());
        event.setEventEndDate(eventRequestForm.getEventEndDate());
        event.setContent(eventRequestForm.getContent());
//        String content = event.getContent();
//        content = content.replaceAll("!\\[[^\\]]*\\]\\([^)]*\\)", ""); // <img> 태그 제거
        // base64로 디코딩 하지 않고 단순히 <img> <p> 태그 replace 후 저장
//        event.setContent(content);
        Optional<CafeCode> op= cafeCodeRepository.findByCode(eventRequestForm.getCode());
        event.setCafeCode(op.get());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        List<EventBoardImageResource> eventBoardImageResourceList = new ArrayList<>();

        // 실제 파일 frontend 이미지 폴더 경로에 저장
        try {
            //1. 썸네일 저장
            for (MultipartFile multipartFile: thumbnail) {
                log.info("requestUploadFilesWithText() - Make file: " + multipartFile.getOriginalFilename());
                String thumbnailRandomName = now.format(dtf);
                String thumbnailReName = 't'+thumbnailRandomName + multipartFile.getOriginalFilename();

                //저장 경로 지정 + 파일네임
                FileOutputStream writer1 = new FileOutputStream("../FourMan-Front/src/assets/event/uploadImgs/" + thumbnailReName);
                log.info("디렉토리에 파일 배치 성공!");

                //파일 저장(저장할때는 byte 형식으로 저장해야 해서 파라미터로 받은 multipartFile 파일들의 getBytes() 메소드 적용해서 저장하는 듯)
                writer1.write(multipartFile.getBytes());
                event.setThumbnailFileName(thumbnailReName);
                writer1.close();
            }



            //2. 상세사진
            if(fileList != null) {
                for (MultipartFile multipartFile : fileList) {
                    log.info("requestUploadFilesWithText() - Make file: " + multipartFile.getOriginalFilename());

                    String fileRandomName = now.format(dtf);
                    String fileReName = 'f' + fileRandomName + multipartFile.getOriginalFilename();

                    FileOutputStream writer2 = new FileOutputStream("../FourMan-Front/src/assets/event/uploadImgs/" + fileReName);
                    log.info("디렉토리에 파일 배치 성공!");

                    writer2.write(multipartFile.getBytes());
                    //이미지리스트 이름 저장
                    writer2.close();
                    EventBoardImageResource eventBoardImageResource = new EventBoardImageResource(fileReName);
                    eventBoardImageResourceList.add(eventBoardImageResource);
                    event.setEventBoardImageResource(eventBoardImageResource);
                }
            }
            eventBoardImageResourceRepository.saveAll(eventBoardImageResourceList);
            eventRepository.save(event);
            return event.getEventId();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<EventListResponse> list() {
        List<Event> eventList = eventRepository.findAllWithEventBoardImageResourceList();
        System.out.println("List<Event> eventList" +eventList);
        List<EventListResponse> eventResponseList = new ArrayList<>();
        for(Event event: eventList){
            eventResponseList.add(new EventListResponse(event.getEventId(),event.getEventName(),
                    event.getEventStartDate(),event.getEventEndDate(),
                    event.getCafeCode().getCafeName(),
                    event.getThumbnailFileName() )   ) ;
        }
        System.out.println("@Events: " + eventResponseList);
        return eventResponseList;
    }

    @Override
    public EventDetailResponse read(Long eventId) {
        Optional<Event> maybeEvent = eventRepository.findById(eventId);
        if (maybeEvent.isEmpty()) {
            log.info("이벤트가 없습니다.");
            return null;
        }
        Event event = maybeEvent.get();
        System.out.println("event.getEventBoardImageResourceList()"+event.getEventBoardImageResourceList());
        EventDetailResponse eventDetailResponse = new EventDetailResponse(
                event.getEventId(),event.getEventName(),
                event.getEventStartDate(),event.getEventEndDate(),
                event.getContent(),event.getCafeCode().getCafeName(),
                event.getThumbnailFileName(),event.getEventBoardImageResourceList() );
        log.info("카페read 서비스 완료");
        return eventDetailResponse;
    }

}
