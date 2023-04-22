package fourman.backend.domain.eventBoard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Event_id")
    private Event event;

    public EventBoardImageResource( String eventBoardImageResourcePath) {
        this.eventBoardImageResourcePath = eventBoardImageResourcePath;

    }

}
