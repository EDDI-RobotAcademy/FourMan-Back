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
    private Date regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_no")
    private QuestionBoard questionBoard;



}
