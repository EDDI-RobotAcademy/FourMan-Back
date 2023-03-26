package fourman.backend.domain.member.entity;


import fourman.backend.domain.cafeIntroduce.entity.Cafe;
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
    @Column(name = "cafe_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false)
    private String code;
    @Getter
    @Column(nullable = false)
    private String cafeName;

    @OneToOne(mappedBy="cafeCode")
    private Cafe cafe;


    public CafeCode(String code, String cafeName){
        this.code = code;
        this.cafeName=cafeName;
    }

    public static CafeCode of(String code , String cafeName) {
        return new CafeCode(code,cafeName);
    }
}
