package fourman.backend.domain.eventBoard.service.response;

import fourman.backend.domain.eventBoard.entity.EventBoardImageResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@ToString
@AllArgsConstructor
public class EventDetailResponse {
    private Long eventId;
    private String eventName;
    private String eventStartDate;
    private String eventEndDate;
    private String content;
    private String cafeName;
    private String thumbnailFileName;
    private List<EventBoardImageResource> eventBoardImageResourceList;

}
