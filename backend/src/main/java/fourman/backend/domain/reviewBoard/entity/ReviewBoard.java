package fourman.backend.domain.reviewBoard.entity;

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

    private String cafeName;

    @Column(length = 128, nullable = false)
    private String title;

    @Column(length = 32, nullable = false)
    private String writer;

    @Lob
    private String content;

    @OneToMany(mappedBy = "reviewBoard", fetch = FetchType.EAGER)
    private List<ReviewBoardImageResource> reviewBoardImageResourceList = new ArrayList<>();

    private Long rating;

    private Long memberId;

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
