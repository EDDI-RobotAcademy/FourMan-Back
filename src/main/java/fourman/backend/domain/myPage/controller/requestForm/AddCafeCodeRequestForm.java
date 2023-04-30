package fourman.backend.domain.myPage.controller.requestForm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddCafeCodeRequestForm {

    private String cafeName;
    private String codeOfCafe;
    private String layoutIndex;
}
