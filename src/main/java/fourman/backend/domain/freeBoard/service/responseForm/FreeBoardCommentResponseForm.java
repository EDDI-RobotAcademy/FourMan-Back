package fourman.backend.domain.freeBoard.service.responseForm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Getter
@ToString
@AllArgsConstructor
public class FreeBoardCommentResponseForm {

    final private Long commentId;
    final private String comment;
    final private String commentWriter;
    final private Date regDate;
    final private Date udpDate;
    final private Long memberId;
    final private Long boardId;

}
