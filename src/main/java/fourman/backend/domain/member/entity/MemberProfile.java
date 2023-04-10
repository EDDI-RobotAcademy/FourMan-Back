package fourman.backend.domain.member.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_profile_id")
    private Long id;

    @Getter
    @Column(nullable = false)
    private String PhoneNumber;

    @Embedded//주소라는 의미의 객체가  MemberProfile 엔티티에 삽입되었다는 의미.
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private MemberProfile(Address address, String phoneNumber) {
        this.address = address;
        this.PhoneNumber = phoneNumber;
    }

    public static MemberProfile of (String city, String street, String addressDetail, String zipcode,String phoneNumber) {
        final Address address = Address.of(city, street, addressDetail, zipcode);
        return new MemberProfile(address , phoneNumber);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}