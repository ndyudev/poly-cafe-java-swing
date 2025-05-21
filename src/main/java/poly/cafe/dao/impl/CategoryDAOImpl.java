package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.entity.Category;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;
import poly.cafe.dao.CategoryDAO;

public class CategoryDAOImpl implements CategoryDAO {

    String createSql = "INSERT INTO Categories(Id, Name) VALUES(?, ?)";
    String updateSql = "UPDATE Categories SET Name=? WHERE Id=?";
    String deleteSql = "DELETE FROM Categories WHERE Id=?";
    String findAllSql = "SELECT * FROM Categories";
    String findByIdSql = "SELECT * FROM Categories WHERE Id=?";

    @Override
    public Category insert(Category entity) {
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
        return XQuery.getEntityList(Category.class, findAllSql);
    }

    @Override
    public Category findById(String id) {
        return XQuery.getSingleBean(Category.class, findByIdSql, id);
    }
}
