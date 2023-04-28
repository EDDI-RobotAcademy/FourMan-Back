package fourman.backend.domain.cafeIntroduce.service.response;

import fourman.backend.domain.cafeIntroduce.entity.CafeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;

@Getter
@RequiredArgsConstructor
public class CafeIntroListResponse {
    final private Long cafeId;
    final private String cafeName;
    final private String cafeAddress;
    final private String cafeTel;
    final private String startTime;
    final private String endTime;
    final private CafeInfo cafeInfo;
    final private double avgRating;
    final private int totalRating;
}
