package fourman.backend.domain.member.entity;

import lombok.Getter;

@Getter
public enum AuthorityType {
    MANAGER("관리자"),
    CAFE("카페회원"),
    MEMBER("일반회원");

    private final String AUTHORITY_TYPE;

    private AuthorityType(String AUTHORITY_TYPE) {
        this.AUTHORITY_TYPE = AUTHORITY_TYPE;
    }

}

