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
public class Bill {
    private Long id;
    private String username;
    private Integer cardId;
    @Builder.Default
    private Date checkin = new Date();
    private Date checkout;
    private int status;
    
    public enum Status {
        Servicing, Completed, Canceled;
    }
    public static final String DATE_PATTERN = "HH:mm:ss dd-MM-yyyy";
}