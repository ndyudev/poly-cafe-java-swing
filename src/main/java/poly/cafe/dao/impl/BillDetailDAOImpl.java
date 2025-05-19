package poly.cafe.dao.impl;

import poly.cafe.dao.BillDetailDAO;

public class BillDetailDAOImpl implements BillDetailDAO {

    String createSql = "…";
    String updateSql = "…";
    String deleteSql = "…";
    String findAllSql = "…";
    String findByIdSql = "…";
s
    String findByBillIdSql = "…";
    String findByDrinkIdSql = "…";

    @Override
    public BillDetail create(BillDetail entity) {
    

    …} 
@Override
    public void update(BillDetail entity) {
    

    …} 
@Override
    public void deleteById(Long id) {
    

    …} 
@Override
    public List<BillDetail> findAll() {
    

    …} 
@Override
    public BillDetail findById(Long id) {
    

    …} 
@Override
    public List<BillDetail> findByBillId(Long billId) {
    

    …}     
@Override
    public List<BillDetail> findByDrinkId(String drinkId) {
    

…} 
}
