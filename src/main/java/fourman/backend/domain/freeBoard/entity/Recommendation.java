package fourman.backend.domain.freeBoard.entity;

import fourman.backend.domain.member.entity.Member;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private FreeBoard freeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private boolean incRecommendationStatus;

    @Column(nullable = false)
    private boolean decRecommendationStatus;

    @CreationTimestamp
    private Date regDate;

    public Recommendation() {
        //기본 생성자가 없어서 객체 인스턴스화가불가해서 하기 생성자 말고 기본생성자도 따로 만들어줌
    }
    public Recommendation(FreeBoard freeBoard, Member member) {
        this.freeBoard = freeBoard;
        this.member = member;
    }
    public void incRecommendation() {
        freeBoard.setRecommendation(freeBoard.getRecommendation() +1);
    }

    public void decRecommendation() {
        freeBoard.setRecommendation(freeBoard.getRecommendation() -1);
    }

    public void incUnRecommendation() {
        freeBoard.setUnRecommendation(freeBoard.getUnRecommendation() +1);
    }

    public void decUnRecommendation() {
        freeBoard.setUnRecommendation(freeBoard.getUnRecommendation() -1);
    }
}
