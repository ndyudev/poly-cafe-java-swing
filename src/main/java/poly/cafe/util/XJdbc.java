package poly.cafe.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class XJdbc {

    private static Connection connection;

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=PS44284_ChauNhatDuy_PolyCafe_ASM;encrypt=true;trustServerCertificate=true;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "ndyudev227";

    // Mở kết nối
    public static Connection openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Kết nối thành công");
            }
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Không thể kết nối CSDL", e);
        }
    }

    // Đóng kết nối
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi đóng kết nối", e);
        }
    }

    // Kiểm tra kết nối có sẵn sàng
    public static boolean isReady() {
        try {
            return (connection != null && !connection.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Tạo PreparedStatement
    private static PreparedStatement prepareStatement(String sql, Object... args) throws SQLException {
        Connection conn = openConnection();
        PreparedStatement stmt = sql.trim().startsWith("{")
                ? conn.prepareCall(sql)
                : conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    // Thực thi INSERT/UPDATE/DELETE
    public static int executeUpdate(String sql, Object... args) {
        try (PreparedStatement stmt = prepareStatement(sql, args)) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thực thi SQL: " + sql, e);
        }
    }

    // Truy vấn SELECT → ResultSet
    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement stmt = prepareStatement(sql, args);
            return stmt.executeQuery(); // ResultSet cần được xử lý bên ngoài
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi truy vấn SQL: " + sql, e);
        }
    }

    // Truy vấn giá trị đơn
    public static <T> T getValue(String sql, Object... args) {
        try (ResultSet rs = executeQuery(sql, args)) {
            if (rs.next()) {
                return (T) rs.getObject(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi truy vấn giá trị", e);
        }
    }

    // Lấy danh sách đối tượng (nếu có dùng thư viện BeanUtils thì sẽ dễ)
    public static <T> List<T> getBeanList(Class<T> type, String sql, Object... args) {
        try (ResultSet rs = executeQuery(sql, args)) {
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                T entity = type.getDeclaredConstructor().newInstance();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String column = meta.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    type.getDeclaredField(column).setAccessible(true);
                    type.getDeclaredField(column).set(entity, value);
                }
                list.add(entity);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi ánh xạ danh sách đối tượng", e);
        }
    }

    // Lấy 1 đối tượng
    public static <T> T getSingleBean(Class<T> type, String sql, Object... args) {
        List<T> list = getBeanList(type, sql, args);
        return list.isEmpty() ? null : list.get(0);
    }

    public static Connection getConnection() {
        return openConnection();
    }

    public static void update(String sql, Object... args) {
        executeUpdate(sql, args);
    }
}
