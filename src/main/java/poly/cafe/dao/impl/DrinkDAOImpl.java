package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.dao.DrinkDAO;
import poly.cafe.entity.Drink;
import poly.cafe.util.XQuery;

public class DrinkDAOImpl implements DrinkDAO { 
    String createSql = "…"; 
    String updateSql = "…"; 
    String deleteSql = "…"; 
    String findAllSql = "…"; 
    String findByIdSql = "…"; 
 
    String findByCategoryIdSql = "SELECT * FROM Drinks WHERE CategoryId=?"; 
 
    @Override 
    public Drink create(Drink entity) {
        return null;
    } 
    @Override 
    public void update(Drink entity) {}
      @Override 
    public void deleteById(String id) {} 
    @Override 
    public List< Drink> findAll() {
        return null;
    } 
    @Override 
    public Drink findById(String id) {
        return null;
    }
    @Override
    public List<Drink> findByCategoryId( String categoryId) {
        return XQuery.getBeanList(Drink.class, findByCategoryIdSql, categoryId);
    }
}