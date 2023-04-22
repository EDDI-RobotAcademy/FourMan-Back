package fourman.backend.domain.freeBoard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fourman.backend.domain.member.entity.Member;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 128, nullable = false)
    private String title;

    @Lob
    private String content;

    @CreationTimestamp
    private Date regDate;

    @UpdateTimestamp
    private Date updDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "freeBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FreeBoardComment> freeBoardCommentList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "freeBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FreeBoardImageResource> freeBoardImageResourceList = new ArrayList<>();

    @ColumnDefault("0")
    private Long viewCnt;

    @ColumnDefault("0")
    private Long recommendation;

    public void setFreeBoardImageResource (FreeBoardImageResource freeBoardImageResource) {
        freeBoardImageResourceList.add(freeBoardImageResource);
        freeBoardImageResource.setFreeBoard(this);
    }

    public void setFreeBoardImageResourceList (List<FreeBoardImageResource> freeBoardImageResourceList) {
        freeBoardImageResourceList.addAll(freeBoardImageResourceList);

        for (int i = 0; i < freeBoardImageResourceList.size(); i++) {
            freeBoardImageResourceList.get(i).setFreeBoard(this);
        }
    }

    public void increaseViewCnt() {
        this.viewCnt++;
    }
    public void increaseRecommendation() {this.recommendation++;}

    public void decreaseRecommendation() {this.recommendation--;}
}
