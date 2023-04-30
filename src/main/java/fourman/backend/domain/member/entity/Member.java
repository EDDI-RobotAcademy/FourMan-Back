package fourman.backend.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
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
    @JsonIgnore
    @OneToOne
    @JoinColumn(name="cafe_code_id")
    private CafeCode cafeCode;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name="manager_code_id")
    private ManagerCode managerCode;

    @Getter
    @JoinColumn(name ="authority_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Authority authority;

    //    // orphanRemoval = true :부모 엔티티에서 자식 엔티티 제거
    @JsonIgnore
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Authentication> authentications = new HashSet<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MemberProfile memberProfile;

    @JsonIgnore
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Point point;

    public Member(String email, String nickName, int birthdate, Authority authority , MemberProfile memberProfile) {
        this.email = email;
        this.nickName = nickName;
        this.birthdate = birthdate;
        this.authority = authority;
        this.memberProfile = memberProfile;
        memberProfile.setMember(this);
    }
    public Member(String email, String nickName, int birthdate, Authority authority,CafeCode cafeCode, MemberProfile memberProfile) {
        this.email = email;
        this.nickName = nickName;
        this.birthdate = birthdate;
        this.authority = authority;
        this.cafeCode=cafeCode;
        this.memberProfile = memberProfile;
        memberProfile.setMember(this);
    }
    public Member(String email, String nickName, int birthdate, Authority authority ,ManagerCode managerCode, MemberProfile memberProfile) {
        this.email = email;
        this.nickName = nickName;
        this.birthdate = birthdate;
        this.authority = authority;
        this.managerCode=managerCode;
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