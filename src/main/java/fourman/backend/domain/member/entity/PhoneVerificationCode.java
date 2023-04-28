package fourman.backend.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class PhoneVerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;
    private String code;
    private LocalDateTime expiration;

    public PhoneVerificationCode() {

    }

    public PhoneVerificationCode(String phoneNumber, String code, LocalDateTime localDateTime) {
        this.phoneNumber=phoneNumber;
        this.code=code;
        this.expiration =localDateTime;
    }


    // getters, setters, constructors
}