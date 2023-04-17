package fourman.backend.domain.member.service.response;

import fourman.backend.domain.member.entity.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberLoginResponse {
    private final String token;
    private final Long id;
    private final String nickName;
    private final AuthorityType authorityName;
    private final Long cafeId;
    private final String code;
    private final String cafeName;
    private final String email;

}
