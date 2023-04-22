package fourman.backend.domain.eventBoard.entity;

import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class EventBoardImageResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String eventBoardImageResourcePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Event_id")
    private Event event;

    public EventBoardImageResource( String eventBoardImageResourcePath) {
        this.eventBoardImageResourcePath = eventBoardImageResourcePath;

    }

}
