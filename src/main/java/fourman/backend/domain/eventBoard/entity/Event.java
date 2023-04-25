package fourman.backend.domain.eventBoard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.CafeCode;
import lombok.*;


import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @Column(length=128, nullable = false)
    private String eventName;
    @Column(nullable = false)
    private String eventStartDate;
    @Column(nullable = false)
    private String eventEndDate;
    @Lob
    private String content;

    private String thumbnailFileName;

    @ManyToOne
    @JoinColumn(name="cafe_id")
    private Cafe cafe;





}
