package poly.cafe.dao;

import java.util.List;

import poly.cafe.entity.Bill;

public interface BillDAO extends CrudDAO<Bill, Long>{ 
    List<Bill> findByUsername(String username); 
    List<Bill> findByCardId(Integer cardId); 
} 
