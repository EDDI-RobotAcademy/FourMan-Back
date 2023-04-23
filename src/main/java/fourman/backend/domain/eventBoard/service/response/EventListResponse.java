package fourman.backend.domain.eventBoard.service.response;

import lombok.Getter;


@Getter
public class EventListResponse {
    private Long eventId;
    private String eventName;
    private String eventStartDate;
    private String eventEndDate;
    private String cafeName;
    private String thumbnailFileName;
//    private List<EventBoardImageResource> eventBoardImageResourceList;


    public EventListResponse(Long eventId, String eventName, String eventStartDate, String eventEndDate, String cafeName, String thumbnailFileName) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.cafeName = cafeName;
        this.thumbnailFileName = thumbnailFileName;
//        this.eventBoardImageResourceList = eventBoardImageResourceList;
    }
}
