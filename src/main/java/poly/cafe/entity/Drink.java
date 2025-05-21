package poly.cafe.entity;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Data 
public class Drink { 
    private String Id; 
    private String name; 
    @Builder.Default 
    private String image = "product.png"; 
    private double unitPrice; 
    private double discount; 
    private boolean available; 
    private String categoryId; 
}
