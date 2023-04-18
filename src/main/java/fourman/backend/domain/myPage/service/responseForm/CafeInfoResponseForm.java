package fourman.backend.domain.myPage.service.responseForm;

import fourman.backend.domain.member.entity.CafeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CafeInfoResponseForm {

    final private Long cafeId;
    final private String cafeName;
    final private String cafeAddress;
    final private String cafeTel;
    final private String startTime;
    final private String endTime;
}
