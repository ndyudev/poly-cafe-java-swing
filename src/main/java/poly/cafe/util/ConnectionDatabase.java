package poly.cafe.util;

import poly.cafe.util.XJdbc;
import java.sql.Connection;

public class ConnectionDatabase {
    public static void main(String[] args) {
        try {
            Connection conn = XJdbc.openConnection();
            if (conn != null) {
                System.out.println("✅ Kết nối thành công!");
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("❌ Kết nối thất bại: " + e.getMessage());
        }
    }
}
