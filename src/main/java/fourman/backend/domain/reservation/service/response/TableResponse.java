package fourman.backend.domain.reservation.service.response;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TableResponse {
    private final String tableName;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Cafe cafe;
}
