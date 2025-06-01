package poly.cafe.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import poly.cafe.entity.Drink;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XStr;
import poly.cafe.dao.DrinkDAO;
import poly.cafe.util.XQuery;

public class DrinkDAOImpl implements DrinkDAO {

    private final String createSql = "INSERT INTO Drinks(Id, Name, Image, UnitPrice, Discount, Available, CategoryId) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final String updateSql = "UPDATE Drinks SET Name=?, Image=?, UnitPrice=?, Discount=?, Available=?, CategoryId=? WHERE Id=?";
    private final String deleteByIdSql = "DELETE FROM Drinks WHERE Id=?";
    
    private final String findAllSql = "SELECT * FROM Drinks";
    private final String findByIdSql = findAllSql + " WHERE Id=?";
    private final String findByCategoryIdSql = findAllSql + " WHERE CategoryId=?";

    @Override
    public Drink create(Drink entity) {
        entity.setId(XStr.getKey());
        Object[] values = {
            entity.getId(),
            entity.getName(),
            entity.getImage(),
            entity.getUnitPrice(),
            entity.getDiscount(),
            entity.isAvailable(),
            entity.getCategoryId()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Drink entity) {
        Object[] values = {
            entity.getName(),
            entity.getImage(),
            entity.getUnitPrice(),
            entity.getDiscount(),
            entity.isAvailable(),
            entity.getCategoryId(),
            entity.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteByIdSql, id);
    }

    @Override
    public List<Drink> findAll() {
        return XQuery.getBeanList(Drink.class, findAllSql);
    }

    @Override
    public Drink findById(String id) {
        return XQuery.getSingleBean(Drink.class, findByIdSql, id);
    }

    @Override
    public List<Drink> findByCategoryId(String categoryId) {
        return XQuery.getBeanList(Drink.class, findByCategoryIdSql, categoryId);
    }    
}