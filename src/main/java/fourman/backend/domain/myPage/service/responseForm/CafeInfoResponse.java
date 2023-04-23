package fourman.backend.domain.myPage.service.responseForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CafeInfoResponse {

    final private Long cafeId;
    final private String cafeName;
    final private String cafeAddress;
    final private String cafeTel;
    final private String startTime;
    final private String endTime;
    final private String subTitle;
    final private String description;
    final private double ratingAverage;
    final private int ratingCount;
    final private double monthTotalSales;
    final private int monthOrderCount;
    final private double dayTotalSales;
    final private int dayOrderCount;
    final private int monthReservationCount;
    final private int dayReservationCount;


}
