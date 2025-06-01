package poly.cafe.util;

import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TimeRange {

    private Date begin = new Date();
    private Date end = new Date();
    
    private TimeRange(LocalDate begin, LocalDate end) {
        this(java.sql.Date.valueOf(begin), java.sql.Date.valueOf(end));
    }
    
    public static TimeRange today() {
        LocalDate begin = LocalDate.now();
        return new TimeRange(begin, begin.plusDays(1));
    }
    
    public static TimeRange thisWeek() {
        LocalDate now = LocalDate.now();
        LocalDate begin = now.minusDays(now.getDayOfWeek().getValue() - 1);
        return new TimeRange(begin, begin.plusDays(7));
    }
    
    public static TimeRange thisMonth() {
        LocalDate now = LocalDate.now();
        LocalDate begin = now.withDayOfMonth(1);
        return new TimeRange(begin, begin.plusDays(now.lengthOfMonth()));
    }
    
    public static TimeRange thisQuarter() {
        LocalDate now = LocalDate.now();
        int firstMonth = now.getMonth().firstMonthOfQuarter().getValue();
        LocalDate begin = now.withMonth(firstMonth).withDayOfMonth(1);
        return new TimeRange(begin, begin.plusMonths(3));
    }
    
    public static TimeRange thisYear() {
        LocalDate now = LocalDate.now();
        LocalDate begin = now.withMonth(1).withDayOfMonth(1);
        return new TimeRange(begin, begin.plusMonths(12));
    }
}