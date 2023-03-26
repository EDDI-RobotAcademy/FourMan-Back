package fourman.backend.domain.cafeIntroduce.controller.requestForm;
import fourman.backend.domain.cafeIntroduce.service.request.CafeIntroRegisterRequest;
import lombok.*;

import java.util.List;
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


        public CafeIntroRegisterRequest toCafeIntroRegisterRequest () {
                return new CafeIntroRegisterRequest( cafeName, cafeAddress,cafeTel, startTime,endTime,subTitle,description);
        }
}
