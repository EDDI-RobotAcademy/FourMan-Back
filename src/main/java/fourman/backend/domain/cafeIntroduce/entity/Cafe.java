package fourman.backend.domain.cafeIntroduce.entity;

import fourman.backend.domain.member.entity.CafeCode;
import lombok.*;


import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Cafe {
//       private String cafeName;
//        private String cafeAddress;
//        private String cafeTel;
//        private String startTime;
//        private String endTime;
//        private String subTitle;
//        private String description;
    @Id
    @Column(name = "cafe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cafeId;
    @Column(nullable = false)
    private String cafeAddress;
    @Column(nullable = false)
    private String cafeTel;
    @Column(length=10 , nullable = false)
    private String startTime;
    @Column(length=10, nullable = false)
    private String endTime;
    @Embedded
    private CafeInfo cafeInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cafe_code_id")
    private CafeCode cafeCode;



    public Cafe( String cafeAddress, String cafeTel, String startTime, String endTime, CafeInfo cafeInfo, CafeCode cafeCode) {
        this.cafeAddress = cafeAddress;
        this.cafeTel = cafeTel;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cafeInfo = cafeInfo;
        this.cafeCode = cafeCode;
    }
}
