package fourman.backend.domain.member.entity;


import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerCode {

    @Id
    @Getter
    @Column(name = "manager_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false,unique=true)
    private String code;

    public ManagerCode(String code){
        this.code = code;
    }

    public static ManagerCode of(String code) {
        return new ManagerCode(code);
    }
}
