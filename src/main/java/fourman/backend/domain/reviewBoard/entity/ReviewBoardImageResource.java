package fourman.backend.domain.reviewBoard.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ReviewBoardImageResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewBoardImageResourcePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewBoard_id")
    private ReviewBoard reviewBoard;

    public ReviewBoardImageResource(String reviewBoardImageResourcePath) {
        this.reviewBoardImageResourcePath = reviewBoardImageResourcePath;
    }

}
