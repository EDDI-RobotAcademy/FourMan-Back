package fourman.backend.domain.noticeBoard.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class NoticeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    @Column(length = 256, nullable= false)
    private String title;

    @Column(length = 50, nullable = false)
    private String writer;

    @Column(length = 50, nullable = false)
    private String notice;

    @Lob
    private String content;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regDate;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updDate;

    private Long memberId;
    @ColumnDefault("0")
    private Long viewCnt;

    public void increaseViewCnt() {
        this.viewCnt++;
    }
}


