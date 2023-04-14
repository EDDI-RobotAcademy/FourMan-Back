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
    @Getter
    @Column(name = "cafe_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false ,unique=true)
    private String code;
    @Getter
    @Column(nullable = false ,unique=true)
    private String cafeName;


    @JsonIgnore
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
