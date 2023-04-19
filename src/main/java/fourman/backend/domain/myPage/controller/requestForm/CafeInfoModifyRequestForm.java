package fourman.backend.domain.myPage.controller.requestForm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CafeInfoModifyRequestForm {

    private Long cafeId;
    private String cafeName;
    private String cafeAddress;
    private String cafeTel;
    private String startTime;
    private String endTime;
    private String subTitle;
    private String description;
}
