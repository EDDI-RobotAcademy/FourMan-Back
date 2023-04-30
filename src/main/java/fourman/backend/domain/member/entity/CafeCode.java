package fourman.backend.domain.member.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CafeCode {

    @Id
    @Column(name = "cafe_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,unique=true)
    private String codeOfCafe;
    @Column(nullable = false ,unique=true)
    private String cafeName;

    @Column(nullable = false )
    private String layoutIndex;


    @JsonIgnore
    @OneToOne(mappedBy="cafeCode", cascade = CascadeType.ALL)
    private Cafe cafe;
    @OneToOne(mappedBy="cafeCode", cascade = CascadeType.ALL)
    private Member member;


    public CafeCode(String code, String cafeName){
        this.codeOfCafe = code;
        this.cafeName=cafeName;
    }

    public static CafeCode of(String code , String cafeName) {
        return new CafeCode(code,cafeName);
    }
}
