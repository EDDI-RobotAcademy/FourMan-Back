package fourman.backend.domain.cafeIntroduce.service.response;

import fourman.backend.domain.cafeIntroduce.entity.CafeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CafeTop3ProductResponse {
    private String productName;
    private Long totalSales;
    private String imageResource;
}
