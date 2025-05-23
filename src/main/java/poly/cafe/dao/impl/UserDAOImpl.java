package poly.cafe.dao.impl;

import poly.cafe.entity.User;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;
import java.util.List;
import poly.cafe.dao.UserDAO;

public class UserDAOImpl implements UserDAO {

    String insertSql = "INSERT INTO Users(Username, Password, Fullname, Photo, Manager, Enabled) VALUES(?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE Users SET Password=?, Fullname=?, Photo=?, Manager=?, Enabled=? WHERE Username=?";
    String deleteSql = "DELETE FROM Users WHERE Username=?";
    String selectAllSql = "SELECT * FROM Users";
    String selectByIdSql = "SELECT * FROM Users WHERE Username=?";
    String selectByNameSql = "SELECT * FROM Users WHERE Fullname LIKE ?";

    @Override
    public void insert(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User hoặc Username không được null hoặc rỗng");
        }
        Object[] values = {
            user.getUsername(),
            user.getPassword(),
            user.getFullname(),
            user.getPhoto(),
            user.isManager(),
            user.isEnabled()
        };
        try {
            XJdbc.executeUpdate(insertSql, values);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm user: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User hoặc Username không được null hoặc rỗng");
        }
        Object[] values = {
            user.getPassword(),
            user.getFullname(),
            user.getPhoto(),
            user.isManager(),
            user.isEnabled(),
            user.getUsername()
        };
        try {
            XJdbc.executeUpdate(updateSql, values);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật user: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được null hoặc rỗng");
        }
        try {
            XJdbc.executeUpdate(deleteSql, username);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa user: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return XQuery.getBeanList(User.class, selectAllSql);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi truy vấn tất cả users: " + e.getMessage(), e);
        }
    }

    @Override
    public User findById(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được null hoặc rỗng");
        }
        try {
            return XQuery.getSingleBean(User.class, selectByIdSql, username);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi truy vấn user theo username: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> findByName(String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        try {
            return XQuery.getBeanList(User.class, selectByNameSql, "%" + keyword + "%");
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi truy vấn users theo tên: " + e.getMessage(), e);
        }
    }
}