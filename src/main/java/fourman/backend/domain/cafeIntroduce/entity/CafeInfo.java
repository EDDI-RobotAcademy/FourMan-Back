package fourman.backend.domain.cafeIntroduce.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

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
    @Column(nullable = true)
    String thumbnailFileName;
    @Column(nullable = true, columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    List<String> cafeImagesName;
    @Column(nullable = true ,length = 128)
    String subTitle;
    @Lob
    String description;

}
