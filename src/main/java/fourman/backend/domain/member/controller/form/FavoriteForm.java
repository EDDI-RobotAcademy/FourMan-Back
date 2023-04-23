package fourman.backend.domain.member.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FavoriteForm {
    private Long cafeId;
    private Long memberId;
    private Boolean isFavorite;

}
