package fourman.backend.domain.reviewBoard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class ReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewBoardId;

    @Column(length = 128, nullable = false)
    private String title;

    @Lob
    private String content;

    @OneToMany(mappedBy = "reviewBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReviewBoardImageResource> reviewBoardImageResourceList = new ArrayList<>();

    private Long rating;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreationTimestamp
    private Date regDate;

    @UpdateTimestamp
    private Date updDate;

    public void setReviewBoardImageResource (ReviewBoardImageResource reviewBoardImageResource) {
        reviewBoardImageResourceList.add(reviewBoardImageResource);
        reviewBoardImageResource.setReviewBoard(this);
    }

    public void setReviewBoardImageResourceList (List<ReviewBoardImageResource> reviewBoardImageResourceList) {
        reviewBoardImageResourceList.addAll(reviewBoardImageResourceList);

        for (int i = 0; i < reviewBoardImageResourceList.size(); i++) {
            reviewBoardImageResourceList.get(i).setReviewBoard(this);
        }
    }

}
