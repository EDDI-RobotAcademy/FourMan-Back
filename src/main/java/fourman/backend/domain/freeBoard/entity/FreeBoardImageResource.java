package fourman.backend.domain.freeBoard.entity;

import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class FreeBoardImageResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String freeBoardImageResourcePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freeBoard_id")
    private FreeBoard freeBoard;

    public FreeBoardImageResource(String freeBoardImageResourcePath) {
        this.freeBoardImageResourcePath = freeBoardImageResourcePath;
    }

}
