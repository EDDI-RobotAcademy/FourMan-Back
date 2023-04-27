package fourman.backend.domain.aop.aspect;

import fourman.backend.domain.member.entity.AuthorityType;
import fourman.backend.domain.member.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean isAuthenticated(Member member) {
        return member != null;
    }
    public boolean isNotAuthenticated(Member member) {
        return member == null ;
    }
    public boolean isCafe(Member member) {
        return member != null && member.getAuthority().getAuthorityName() == AuthorityType.CAFE;
    }

    public boolean isManager(Member member) {
        return member != null && member.getAuthority().getAuthorityName() == AuthorityType.MANAGER;
    }
}