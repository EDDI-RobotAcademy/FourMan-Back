package fourman.backend.domain.eventBoard.entity;

import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.reviewBoard.entity.ReviewBoardImageResource;
import lombok.*;


import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @Column(length=128, nullable = false)
    private String eventName;
    @Column(nullable = false)
    private String eventStartDate;
    @Column(nullable = false)
    private String eventEndDate;
    @Lob
    private String content;

    private String thumbnailFileName;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cafe_code_id")
    private CafeCode cafeCode;
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EventBoardImageResource> eventBoardImageResourceList = new ArrayList<>();

    public void setEventBoardImageResource (EventBoardImageResource eventBoardImageResource) {
        eventBoardImageResourceList.add(eventBoardImageResource);
        eventBoardImageResource.setEvent(this);
    }




}
