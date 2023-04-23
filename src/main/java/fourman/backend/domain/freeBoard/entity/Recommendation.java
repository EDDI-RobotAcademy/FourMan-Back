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
    private boolean status;

    @CreationTimestamp
    private Date regDate;

    public Recommendation() {
        //기본 생성자가 없어서 객체 인스턴스화가불가해서 하기 생성자 말고 기본생성자도 따로 만들어줌
    }
    public Recommendation(FreeBoard freeBoard, Member member) {
        this.freeBoard = freeBoard;
        this.member = member;
        this.status = true;
    }

    public void unIncRecommendationBoard(FreeBoard freeBoard) {
        this.status = false;
        freeBoard.setRecommendation(freeBoard.getRecommendation() -1);
    }

    public void unDecRecommendationBoard(FreeBoard freeBoard) {
        this.status = false;
        freeBoard.setRecommendation(freeBoard.getRecommendation() +1);
    }
}
