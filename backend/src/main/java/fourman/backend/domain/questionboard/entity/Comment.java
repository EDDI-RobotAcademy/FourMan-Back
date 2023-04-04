package fourman.backend.domain.questionboard.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import fourman.backend.domain.member.entity.Member;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Lob
    private String comment;

    @Column(length = 50, nullable = false)
    private String commentWriter;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regDate;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date udpDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="board_id")
    private QuestionBoard questionBoard;

    private Long memberId;



}
