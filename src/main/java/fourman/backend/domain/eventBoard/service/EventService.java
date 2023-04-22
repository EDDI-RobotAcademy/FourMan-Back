package fourman.backend.domain.eventBoard.service;

import fourman.backend.domain.eventBoard.controller.requestForm.EventRequestForm;
import fourman.backend.domain.eventBoard.service.response.EventDetailResponse;
import fourman.backend.domain.eventBoard.service.response.EventListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    Long registerEvent(List<MultipartFile> thumbnail, EventRequestForm eventRequestForm);
    List<EventListResponse> list();
    EventDetailResponse read(Long eventId);
    ResponseEntity<?> uploadImage(MultipartFile file);

}
