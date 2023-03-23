package fourman.backend.domain.member.entity;


import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CafeCode {

    @Id
    @Getter
    @Column(name = "cafeCode_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false)
    private String code;

    public CafeCode(String code){
        this.code = code;
    }

    public static CafeCode of(String code) {
        return new CafeCode(code);
    }
}
