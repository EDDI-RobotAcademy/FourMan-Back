package fourman.backend.domain.myPage.service.responseForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class PointDetailsResponse {

    final private Long infoId;
    final private String nickName;
    final private String history;
    final private Date date;
    final private Long amount;
    final private Boolean isUse;

    public PointDetailsResponse(Long infoId, String nickName, String history, Date date, Long amount, boolean isUse) {
        this.infoId = infoId;
        this.nickName = nickName;
        this.history = history;
        this.date = date;
        this.amount = amount;
        this.isUse = isUse;
    }
}
