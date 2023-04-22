package fourman.backend.domain.eventBoard.service;

import fourman.backend.domain.eventBoard.controller.requestForm.EventRequestForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {
    Long registerEvent(List<MultipartFile> thumbnail, List<MultipartFile> fileList, EventRequestForm eventRequestForm);


}
