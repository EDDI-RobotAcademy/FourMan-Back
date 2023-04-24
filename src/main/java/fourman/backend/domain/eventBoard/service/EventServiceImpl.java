package fourman.backend.domain.eventBoard.service;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.eventBoard.controller.requestForm.EventRequestForm;
import fourman.backend.domain.eventBoard.entity.Event;
import fourman.backend.domain.eventBoard.repository.EventRepository;
import fourman.backend.domain.eventBoard.service.response.EventDetailResponse;
import fourman.backend.domain.eventBoard.service.response.EventListResponse;
import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.repository.CafeCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final CafeCodeRepository cafeCodeRepository;
    @Override
    public Long registerEvent(List<MultipartFile> thumbnail, EventRequestForm eventRequestForm) {
        // 1. event 저장
        Event event = new Event();
        event.setEventName(eventRequestForm.getEventName());
        event.setEventStartDate(eventRequestForm.getEventStartDate());
        event.setEventEndDate(eventRequestForm.getEventEndDate());
        event.setContent(eventRequestForm.getContent());
        Optional<CafeCode> op= cafeCodeRepository.findByCode(eventRequestForm.getCode());
        event.setCafeCode(op.get());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // 실제 파일 frontend 이미지 폴더 경로에 저장
        try {
            //1. 썸네일 저장
            for (MultipartFile multipartFile: thumbnail) {
                log.info("requestUploadFilesWithText() - Make file: " + multipartFile.getOriginalFilename());
                String thumbnailRandomName = now.format(dtf);
                String thumbnailReName = 't'+thumbnailRandomName + multipartFile.getOriginalFilename();

                //저장 경로 지정 + 파일네임
                FileOutputStream writer1 = new FileOutputStream("../FourMan-Front/public/assets/event/uploadImgs/" + thumbnailReName);
                log.info("디렉토리에 파일 배치 성공!");

                //파일 저장(저장할때는 byte 형식으로 저장해야 해서 파라미터로 받은 multipartFile 파일들의 getBytes() 메소드 적용해서 저장하는 듯)
                writer1.write(multipartFile.getBytes());
                event.setThumbnailFileName(thumbnailReName);
                writer1.close();
            }

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
        List<Event> eventList = eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventId"));
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
        EventDetailResponse eventDetailResponse = new EventDetailResponse(
                event.getEventId(),event.getEventName(),
                event.getEventStartDate(),event.getEventEndDate(),
                event.getContent(),event.getCafeCode().getCafeName(),
                event.getThumbnailFileName() );
        log.info("카페read 서비스 완료");
        return eventDetailResponse;
    }
    @Override
    public ResponseEntity<?> uploadImage(MultipartFile file){
        try {
            // 이미지 저장 경로 설정
            String folderPath = "../FourMan-Front/public/assets/event/uploadImgs/";
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String fileRandomName = now.format(dtf);
            String fileReName = 'f' + fileRandomName + file.getOriginalFilename();
            log.info("uploadImage파일 저장경로설정");
            // 이미지 파일 저장
            File outputFile = new File(folderPath + fileReName);
            outputFile.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(file.getBytes());
                log.info("uploadImage파일 저장");
            }
            String imageUrl = "/assets/event/uploadImgs/" + fileReName;
            System.out.println("imageUrl "+imageUrl);
            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while uploading image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Cafe getCafeByEventId(Long eventId) {
        Optional<Event> maybeEvent=eventRepository.findById(eventId);
        if(maybeEvent.isPresent()){
            return maybeEvent.get().getCafeCode().getCafe();
        }
        return null;
    }

    @Override
    public Long modifyEvent(Long eventId, List<MultipartFile> thumbnail, EventRequestForm eventRequestForm) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent()) {
            log.info("이벤트가존재하지 않습니다.");
            return null;
        }
        Event event = optionalEvent.get();

        // 1. event 업데이트
        event.setEventName(eventRequestForm.getEventName());
        event.setEventStartDate(eventRequestForm.getEventStartDate());
        event.setEventEndDate(eventRequestForm.getEventEndDate());
        event.setContent(eventRequestForm.getContent());
        Optional<CafeCode> op= cafeCodeRepository.findByCode(eventRequestForm.getCode());
        event.setCafeCode(op.get());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // 실제 파일 frontend 이미지 폴더 경로에 저장
        try {
            // 썸네일 저장
            if (thumbnail != null && !thumbnail.isEmpty()) {
                MultipartFile multipartFile = thumbnail.get(0);
                log.info("modifyEvent() - Make file: " + multipartFile.getOriginalFilename());
                String thumbnailRandomName = now.format(dtf);
                String thumbnailReName = 't' + thumbnailRandomName + multipartFile.getOriginalFilename();

                // 저장 경로 지정 + 파일네임
                FileOutputStream writer1 = new FileOutputStream("../FourMan-Front/public/assets/event/uploadImgs/" + thumbnailReName);
                log.info("디렉토리에 파일 배치 성공!");

                // 파일 저장
                writer1.write(multipartFile.getBytes());
                event.setThumbnailFileName(thumbnailReName);
                writer1.close();
            }

            // 수정된 이벤트 저장
            eventRepository.save(event);
            return event.getEventId();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }


}
