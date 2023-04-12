package fourman.backend.domain.myPage.myInfo.controller.requestForm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberInfoModifyRequestForm {

    private String nickName;
    private int birthdate;
    private String phoneNumber;
    private String city;
    private String street;
    private String addressDetail;
    private String zipcode;
}
