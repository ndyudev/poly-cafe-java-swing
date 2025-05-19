package poly.cafe.util;

import poly.cafe.entity.Category;
import java.sql.ResultSet;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        Test1();

    }

    public static void Test1() {
        String sql = "INSERT INTO Categories (Id, Name) VALUES (?, ?)";
        XJdbc.executeUpdate(sql, "C01", "Loại 1");
        XJdbc.executeUpdate(sql, "C02", "Loại 2");
    }

    public static void Test2() {
        String sql = "SELECT * FROM Categories WHERE Name LIKE ?";
        ResultSet rs = XJdbc.executeQuery(sql, "%Loại%");
    }

    public static void Test3() {
        String sql = "SELECT * FROM Categories WHERE Name LIKE ?";
        List<Category> beans = XQuery.getBeanList(Category.class, sql, "%Loại%");
    }

    public static void Test4() {
        String sql = "SELECT * FROM Categories WHERE Id=?";
        Category bean = XQuery.getSingleBean(Category.class, sql, "C02");
    }

    public static void Test5() {
        String sql = "SELECT max(Id) FROM Categories WHERE Name LIKE ?";
        String id = XJdbc.getValue(sql, "%Loại%");
    }

}
