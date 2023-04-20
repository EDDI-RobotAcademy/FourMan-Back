package fourman.backend.domain.questionboard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fourman.backend.domain.member.entity.Member;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class QuestionBoard {


    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 256, nullable= false)
    private String title;

    @Column(length = 50, nullable = false)
    private String writer;

    @Column(length = 50, nullable = false)
    private String questionType;

    @Lob
    private String content;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regDate;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "questionBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> freeBoardCommentList = new ArrayList<>();

    private boolean secret;
    @ColumnDefault("0")
    private Long viewCnt;

    public void increaseViewCnt() {
        this.viewCnt++;
    }

}
