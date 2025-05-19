package poly.cafe.dao.impl;

import poly.cafe.dao.BillDAO;

public class BillDAOImpl implements BillDAO {

    String createSql = "…";
    String updateSql = "…";
    String deleteSql = "…";
    String findAllSql = "…";
    String findByIdSql = "…";

    String findByUsernameSql = "…";
    String findByCardIdSql = "…";

    @Override
    public Bill create(Bill entity) {
    

    …} 
    @Override
    public void update(Bill entity) {
    

    …} 
    @Override
    public void deleteById(String id) {
    

    …} 
    @Override
    public List< Bill> findAll() {
    

    …} 
    @Override
    public Bill findById(String id) {
    

    …} 
 
    @Override
    public List<Bill> findByUsername(String username) {
    

    …}     
    @Override
    public List<Bill> findByCardId(Integer cardId) {
    

…}     
}
