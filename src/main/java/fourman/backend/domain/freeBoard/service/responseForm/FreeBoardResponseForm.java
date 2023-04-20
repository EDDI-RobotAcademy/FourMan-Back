package fourman.backend.domain.freeBoard.service.responseForm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fourman.backend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Getter
@ToString
@AllArgsConstructor
public class FreeBoardResponseForm {

    final private Long boardId;
    final private String title;
    final private String writer;
    final private String content;
    final private Date regDate;
    final private Date updDate;
    final private Long memberId;
    final private Long viewCnt;
    final private Long recommendation;
}
