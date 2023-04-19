package fourman.backend.domain.freeBoard.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 128, nullable = false)
    private String title;

    @Column(length = 32, nullable = false)
    private String writer;

    @Lob
    private String content;

    @CreationTimestamp
    private Date regDate;

    @UpdateTimestamp
    private Date updDate;

    @Column(nullable = false)
    private Long memberId;

    @ColumnDefault("0")
    private Long viewCnt;

    @ColumnDefault("0")
    private Long recommendation;

    public void increaseViewCnt() {
        this.viewCnt++;
    }
    public void increaseRecommendation() {this.recommendation++;}

    public void decreaseRecommendation() {this.recommendation--;}
}
