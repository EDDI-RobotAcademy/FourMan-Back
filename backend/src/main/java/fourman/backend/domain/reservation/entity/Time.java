package fourman.backend.domain.reservation.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Time {
    @Id
    @Column(name = "time_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TimeId;

    @Column
    private String time;

}