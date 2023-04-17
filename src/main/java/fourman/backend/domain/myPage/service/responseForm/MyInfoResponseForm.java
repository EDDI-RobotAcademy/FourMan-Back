package fourman.backend.domain.myPage.service.responseForm;

import fourman.backend.domain.member.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MyInfoResponseForm {

    final private String nickName;
    final private int birthdate;
    final private String email;
    final private String PhoneNumber;
    final private Address address;

}
