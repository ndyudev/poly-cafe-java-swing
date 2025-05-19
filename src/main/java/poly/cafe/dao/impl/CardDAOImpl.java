
package poly.cafe.dao.impl;

import poly.cafe.dao.CardDAO;


public class CardDAOImpl implements CardDAO { 
    String createSql = "…"; 
    String updateSql = "…"; 
    String deleteSql = "…"; 
    String findAllSql = "…"; 
    String findByIdSql = "…"; 
 
    @Override 
    public Card create(Card entity) {…} 
    @Override 
    public void update(Card entity) {…} 
    @Override 
    public void deleteById(String id) {…} 
    @Override 
    public List< Card> findAll() {…} 
    @Override 
    public Card findById(String id) {…} 
} 
