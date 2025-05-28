package poly.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Card {
    private Integer id;
    private int status;
}
