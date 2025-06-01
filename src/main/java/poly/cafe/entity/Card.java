package poly.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Card {
    private Integer id;
    private int status;
    
    public enum Status {
        Operating, Error, Lose;
    }
}