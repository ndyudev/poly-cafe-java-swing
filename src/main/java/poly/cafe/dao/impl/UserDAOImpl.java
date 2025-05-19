package poly.cafe.dao.impl;

import poly.cafe.dao.UserDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import poly.cafe.entity.Category;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;


public class UserDAOImpl implements UserDAO {

    String insertSql = "INSERT INTO Users(Username, Password, Fullname) VALUES(?, ?, ?)";
    String updateSql = "UPDATE Users SET Password=?, Fullname=? WHERE Username=?";
    String deleteSql = "DELETE FROM Users WHERE Username=?";
    String selectAllSql = "SELECT * FROM Users";
    String selectByIdSql = "SELECT * FROM Users WHERE Username=?";
    String selectByNameSql = "SELECT * FROM Users WHERE Fullname LIKE ?";

    @Override
    public Category create(Category entity) {
        Object[] values = {
            entity.getId(),
            entity.getName()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Category entity) {
        Object[] values = {
            entity.getName(),
            entity.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<Category> findAll() {
        try {
            return XQuery.getEntityList(Category.class, findAllSql);
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Category findById(String id) {
        return XQuery.getSingleBean(Category.class, findByIdSql, id);
    }
}