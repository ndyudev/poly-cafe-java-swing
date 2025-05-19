package poly.cafe.util;

import java.sql.ResultSet;

public class ConnectionDatabase {
    public static void main(String[] args) {
        // Mở kết nối
        XJdbc.openConnection();

        testConnection();
        testSelect();
        testGetValue();
        testUpdate();

        // Đóng kết nối
        XJdbc.closeConnection();
    }

    public static void testConnection() {
        System.out.println("=== TEST CONNECTION ===");
        if (XJdbc.isReady()) {
            System.out.println("✅ Đã kết nối database thành công!");
        } else {
            System.out.println("❌ Kết nối thất bại!");
        }
        System.out.println();
    }

    public static void testSelect() {
        System.out.println("=== TEST SELECT ===");
        String sql = "SELECT * FROM Drinks";
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            while (rs.next()) {
                System.out.println("🥤 Tên đồ uống: " + rs.getString("DrinkName") +
                                   " | 💵 Giá: " + rs.getDouble("UnitPrice"));
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi truy vấn SELECT:");
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void testGetValue() {
        System.out.println("=== TEST GET VALUE ===");
        String sql = "SELECT MAX(UnitPrice) FROM Drinks";
        try {
            Double maxPrice = XJdbc.getValue(sql);
            System.out.println("🤑 Giá cao nhất là: " + maxPrice);
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi lấy giá trị:");
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void testUpdate() {
        System.out.println("=== TEST UPDATE ===");
        String sql = "UPDATE Drinks SET UnitPrice = UnitPrice + 1 WHERE UnitPrice < ?";
        try {
            int rows = XJdbc.executeUpdate(sql, 10.0);
            System.out.println("🛠️ Đã cập nhật " + rows + " dòng!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi UPDATE:");
            e.printStackTrace();
        }
        System.out.println();
    }
}
