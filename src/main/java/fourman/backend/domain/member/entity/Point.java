package fourman.backend.domain.member.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Point {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @Column
    private Long point;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "point", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PointInfo> infoList;

    public Point(Long point, Member member, List<PointInfo> infoList) {
        this.point = point;
        this.member = member;
        this.infoList = infoList;
        for(PointInfo pointInfo : infoList) {
            pointInfo.setPoint(this);
        }
    }
}
