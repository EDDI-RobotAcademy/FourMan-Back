package fourman.backend.domain.member.entity;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
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
    @Getter
    @Column(nullable = false)
    private String profileImage;

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
        MemberProfile memberProfile = new MemberProfile(address , phoneNumber);
        memberProfile.profileImage = "defaultProfileImage.png";
        return memberProfile;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Address getAddress() { return address; }
}