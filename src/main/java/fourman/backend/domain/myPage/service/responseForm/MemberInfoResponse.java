package fourman.backend.domain.myPage.service.responseForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class MemberInfoResponse {

    final private Long id;
    final private String nickName;
    final private String authorityName;
    final private String email;
    final private String phoneNumber;
    final private Long point;



}
