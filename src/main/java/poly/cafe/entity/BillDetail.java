package poly.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BillDetail {
    private Long id;
    private Long billId;
    private String drinkId;
    private double unitPrice;
    private double discount;
    private int quantity;
    
    private String drinkName;
}