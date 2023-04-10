package fourman.backend.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Member {

    @Id
    @Getter
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false)
    private String email;

    @Getter
    @Column(nullable = false)
    private String nickName;

    @Getter
    @Column(nullable = false)
    private int birthdate;

    @Getter
    private String code;

    @Getter
    @JsonIgnore
    @JoinColumn(name ="authority_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Authority authority;

    //    // orphanRemoval = true :부모 엔티티에서 자식 엔티티 제거
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Authentication> authentications = new HashSet<>();


    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MemberProfile memberProfile;

    public Member(String email, String nickName, int birthdate, Authority authority,String code,  MemberProfile memberProfile) {
        this.email = email;
        this.nickName = nickName;
        this.birthdate = birthdate;
        this.authority = authority;
        this.code=code;
        this.memberProfile = memberProfile;
        memberProfile.setMember(this);
    }


    public boolean isRightPassword(String plainToCheck) {
        final Optional<Authentication> maybeBasicAuth = findBasicAuthentication();

        if (maybeBasicAuth.isPresent()) {
            System.out.println("maybeBasicAuth가 존재함");
            final BasicAuthentication authentication = (BasicAuthentication) maybeBasicAuth.get();
            return authentication.isRightPassword(plainToCheck);
        }
        System.out.println("maybeBasicAuth 가 존재하지않음");
        return false;
    }

    private Optional<Authentication> findBasicAuthentication () {
        return authentications
                .stream()
                .filter(authentication -> authentication instanceof BasicAuthentication)
                .findFirst();
        //true가 나오는 경우.
        // child instanceof Parent
        // parent instanceof Parent
        // child instanceof Child
    }
}