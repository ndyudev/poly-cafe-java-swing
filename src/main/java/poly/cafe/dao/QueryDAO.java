package poly.cafe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import poly.cafe.util.XJdbc;

public interface QueryDAO {
    /**
     * Truy vấn danh sách thực thể
     *
     * @param sql câu lệnh SQL chứa tham số
     * @param values mảng giá trị cung cấp cho các tham số
     * @return Kết quả truy vấn
     */
    default <T> List<T> getEntityList(String sql, Object... values) {
        List<T> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, values);
            while (rs.next()) {
                list.add(this.readEntity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Truy vấn một thực thể
     *
     * @param sql câu lệnh SQL chứa tham số
     * @param values mảng giá trị cung cấp cho các tham số
     * @return Kết quả truy vấn
     */
    default <T> T getSingleEntity(String sql, Object... values) {
        List<T> list = this.getEntityList(sql, values);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    /**
     * Đọc và tạo thực thể từ dữ liệu bản ghi hiện tại
     * 
     * @param rs là ResultSet chứa dữ liệu
     * @return thực thể chứa dữ liệu bản ghi hiện tại
     * @throws SQLException lỗi đọc dữ liệu từ bản ghi
     */
    <T> T readEntity(ResultSet rs) throws SQLException;
}