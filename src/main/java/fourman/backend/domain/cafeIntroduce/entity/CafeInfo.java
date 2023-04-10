package fourman.backend.domain.cafeIntroduce.entity;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

import fourman.backend.domain.utility.cafe.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CafeInfo {
    String thumbnailFileName;

    @Convert(converter = StringListConverter.class)
    List<String> cafeImagesName;

    String subTitle;

    String description;

}
