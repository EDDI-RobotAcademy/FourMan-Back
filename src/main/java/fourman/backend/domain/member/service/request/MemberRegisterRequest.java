package fourman.backend.domain.member.service.request;

import fourman.backend.domain.member.entity.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberRegisterRequest {


    private final String email;
    private final String password;
    private final String nickName;
    private final int birthdate;
    private final String phoneNumber;
    private final AuthorityType authorityName;
    private final String code;
    private String city;
    private String street;
    private String addressDetail;
    private String zipcode;


    public MemberRegisterRequest(String email, String password, String nickName, int birthdate, AuthorityType authorityName, String code, String city, String street, String addressDetail, String zipcode, String phoneNumber){
        this.email= email;
        this.password = password;
        this.nickName= nickName;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.authorityName = authorityName;
        this.code=code;
        this.city= city;
        this.street = street;
        this.addressDetail = addressDetail;
        this.zipcode = zipcode;

    }


    public Member toMember () {
        return new Member(
                email,
                nickName,
                birthdate,
                Authority.ofMember(authorityName),
                MemberProfile.of(city, street, addressDetail, zipcode,phoneNumber)
        );
    }
    public Member toCafeMember (CafeCode cafeCode) {
        return new Member(
                email,
                nickName,
                birthdate,
                Authority.ofMember(authorityName),
                cafeCode,
                MemberProfile.of(city, street, addressDetail, zipcode,phoneNumber)
        );
    }
    public Member toManagerMember (ManagerCode managerCode) {
        return new Member(
                email,
                nickName,
                birthdate,
                Authority.ofMember(authorityName),
                managerCode,
                MemberProfile.of(city, street, addressDetail, zipcode,phoneNumber)
        );
    }

}