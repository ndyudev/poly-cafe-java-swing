package poly.cafe.dao.impl;

import poly.cafe.dao.DrinkDAO;

public class DrinkDAOImpl implements DrinkDAO {

    String createSql = "…";
    String updateSql = "…";
    String deleteSql = "…";
    String findAllSql = "…";
    String findByIdSql = "…";

    String findByCategoryIdSql = "SELECT * FROM Drinks WHERE CategoryId=?";

    @Override
    public Drink create(Drink entity) {
    

    …} 
    @Override
    public void update(Drink entity) {
    

    …}
    
 @Override
    public void deleteById(String id) {
    

    …} 
    @Override
    public List< Drink> findAll() {
    

    …} 
    @Override
    public Drink findById(String id) {
    

    …} 
 
    @Override
    public List<Drink> findByCategoryId(String categoryId) {
        return XQuery.getBeanList(Drink.class, findByCategoryIdSql, categoryId);
    }
}
