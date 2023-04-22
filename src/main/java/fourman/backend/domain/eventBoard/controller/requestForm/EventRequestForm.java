package fourman.backend.domain.eventBoard.controller.requestForm;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestForm {

        private String eventName;
        private String eventStartDate;
        private String eventEndDate;
        private String content;
        private String code;

}
