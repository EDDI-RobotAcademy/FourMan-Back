package fourman.backend.domain.member.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PointInfo {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    @Column(nullable = false)
    private String history;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private boolean isUse;

    @CreationTimestamp
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    public PointInfo(String history, Long amount, boolean isUse) {
        this.history = history;
        this.amount = amount;
        this.isUse = isUse;
    }

    public void setPointInfo(String history, Long amount, boolean isUse, Point point) {
        this.history = history;
        this.amount = amount;
        this.isUse = isUse;
        this.point = point;
    }

}
