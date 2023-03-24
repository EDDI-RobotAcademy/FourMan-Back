package fourman.backend.domain.member.service.response;

import fourman.backend.domain.member.entity.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberLoginResponse {
    private String token;
    private Long id;
    private String username;
    private AuthorityType authorityName;
    private String code;
}
