package poly.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Category {

    private String id;
    private String name;
}
