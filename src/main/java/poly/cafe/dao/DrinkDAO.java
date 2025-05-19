package poly.cafe.dao;

import java.sql.*;
import java.util.*;
import poly.cafe.entity.Drinks;
import poly.cafe.util.XJdbc;
    
public class DrinkDAO {

    public List<Drinks> selectAll() {
        List<Drinks> list = new ArrayList<>();
        String sql = "SELECT * FROM Drinks";
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            while (rs.next()) {
                Drinks d = new Drinks();
                d.setId(rs.getString("Id"));
                d.setName(rs.getString("Name"));
//                d.setUnitPrice(rs.getDouble("UnitPrice"));
//                d.setDiscount(rs.getDouble("Discount"));
                d.setImage(rs.getString("Image"));
                d.setAvailable(rs.getBoolean("Available"));
//                d.setCategoryId(rs.getString("CategoryId"));
                list.add(d);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Có thể thêm các hàm insert/update/delete nếu cần
}
