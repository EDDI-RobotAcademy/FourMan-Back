package fourman.backend.domain.cafeIntroduce.controller.requestForm;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CafeIntroRequestForm{

        private String cafeName;
        private String cafeAddress;
        private String cafeTel;
        private String startTime;
        private String endTime;
        private String subTitle;
        private String description;
        private String code;


}
