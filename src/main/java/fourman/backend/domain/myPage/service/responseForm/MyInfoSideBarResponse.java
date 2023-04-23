package fourman.backend.domain.myPage.service.responseForm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyInfoSideBarResponse {

    final private String nickName;
    final private String memberType;
    final private Long point;
}
