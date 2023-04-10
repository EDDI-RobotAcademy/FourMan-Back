package fourman.backend.domain.member.entity;
/*
lazy로하면db에서 미리 땡겨오지않음 lazy가 좋다. 근본은 lazy임

*/

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString(exclude = "member")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//상속 -단일 테이블 전략
@DiscriminatorColumn(name = "authentication_type")//부모 클래스에 선언한다. 하위 클래스를 구분하는 용도의 컬럼이다. 관례는 default = DTYPE
//예를들면 @DiscriminatorColumn(name = “deal_type”), 즉 deal_type 필드의 값이 뭐냐에 따라서 어떤 엔터티를 사용할지가 정해진다고 보면 된다.
public abstract class Authentication {

    public static final String BASIC_AUTH = "BASIC";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "authentication_type", nullable = false, insertable = false, updatable = false)
    private String authenticationType;

    public Authentication (Member member, String authenticationType) {
        this.member = member;
        this.authenticationType = authenticationType;
    }
}