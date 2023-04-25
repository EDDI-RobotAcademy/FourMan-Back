package fourman.backend.domain.cafeIntroduce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fourman.backend.domain.eventBoard.entity.Event;
import fourman.backend.domain.member.entity.CafeCode;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Cafe {
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
    @OneToOne
    @JoinColumn(name="cafe_code_id")
    private CafeCode cafeCode;
    @JsonIgnore
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();


    public Cafe( String cafeAddress, String cafeTel, String startTime, String endTime, CafeInfo cafeInfo, CafeCode cafeCode) {
        this.cafeAddress = cafeAddress;
        this.cafeTel = cafeTel;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cafeInfo = cafeInfo;
        this.cafeCode = cafeCode;
    }
}
