package fourman.backend.domain.member.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable//주소라는 의미의 객체로 표현하여 MemberProfile 엔티티에 삽입 가능.
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String zipcode;

    public static Address of(String city, String street, String addressDetail, String zipcode) {
        return new Address(city, street, addressDetail, zipcode);// Address 인스턴스를 생성해주는 메서드
    }
}