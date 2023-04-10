package fourman.backend.domain.cafeIntroduce.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CafeIntroRegisterRequest {
    private String cafeName;
    private String cafeAddress;
    private String cafeTel;
    private String startTime;
    private String endTime;
    private String subTitle;
    private String description;

    public CafeIntroRegisterRequest(String cafeName, String cafeAddress, String cafeTel, String startTime, String endTime, String subTitle, String description) {
        this.cafeName = cafeName;
        this.cafeAddress = cafeAddress;
        this.cafeTel = cafeTel;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subTitle = subTitle;
        this.description = description;
    }
}
