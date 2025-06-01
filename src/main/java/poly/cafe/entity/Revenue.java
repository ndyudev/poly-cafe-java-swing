package poly.cafe.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Revenue {
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class ByCategory {

        private String category;
        private double revenue;
        private int quantity;
        private double minPrice;
        private double maxPrice;
        private double avgPrice;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class ByUser {

        private String user;
        private double revenue;
        private int quantity;
        private Date firstTime;
        private Date lastTime;
    }
}
