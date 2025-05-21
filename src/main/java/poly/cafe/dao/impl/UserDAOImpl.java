package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.dao.UserDAO;
import poly.cafe.entity.User;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;

public class UserDAOImpl implements UserDAO {

    String createSql = "INSERT INTO Users (Username, Password, Enabled, Fullname, Photo, Manager) VALUES (?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE Users SET Password=?, Enabled=?, Fullname=?, Photo=?, Manager=? WHERE Username=?";
    String deleteSql = "DELETE FROM Users WHERE Username=?";
    String findAllSql = "SELECT * FROM Users";
    String findByIdSql = "SELECT * FROM Users WHERE Username=?";
    String findByFullnameSql = "SELECT * FROM Users WHERE Fullname LIKE ?";

    @Override
    public User create(User entity) {
        XJdbc.executeUpdate(createSql,
                entity.getUsername(),
                entity.getPassword(),
                entity.isEnabled(),
                entity.getFullname(),
                entity.getPhoto(),
                entity.isManager()
        );
        return entity;
    }

    @Override
    public void update(User entity) {
        XJdbc.executeUpdate(updateSql,
                entity.getPassword(),
                entity.isEnabled(),
                entity.getFullname(),
                entity.getPhoto(),
                entity.isManager(),
                entity.getUsername()
        );
    }

    @Override
    public void deleteById(String username) {
        XJdbc.executeUpdate(deleteSql, username);
    }

    @Override
    public List<User> findAll() {
        return XQuery.getEntityList(User.class, findAllSql);
    }

    @Override
    public User findById(String username) {
        return XQuery.getSingleBean(User.class, findByIdSql, username);
    }

    @Override
    public List<User> findByFullname(String fullname) {
        return XQuery.getBeanList(User.class, findByFullnameSql, "%" + fullname + "%");
    }
}
