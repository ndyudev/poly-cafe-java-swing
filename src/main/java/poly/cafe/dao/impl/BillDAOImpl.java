package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.dao.BillDAO;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;
import poly.cafe.entity.Bill;

public class BillDAOImpl implements BillDAO {

    String createSql = "INSERT INTO Bills (Username, CardId, Checkin, Checkout, Status) VALUES (?, ?, ?, ?, ?)";
    String updateSql = "UPDATE Bills SET Username=?, CardId=?, Checkin=?, Checkout=?, Status=? WHERE Id=?";
    String deleteSql = "DELETE FROM Bills WHERE Id=?";
    String findAllSql = "SELECT * FROM Bills";
    String findByIdSql = "SELECT * FROM Bills WHERE Id=?";
    String findByUsernameSql = "SELECT * FROM Bills WHERE Username=?";
    String findByCardIdSql = "SELECT * FROM Bills WHERE CardId=?";

    @Override
    public Bill create(Bill entity) {
        XJdbc.executeUpdate(createSql,
                entity.getUsername(),
                entity.getCardId(),
                entity.getCheckin(),
                entity.getCheckout(),
                entity.getStatus()
        );
        return entity;
    }

    @Override
    public void update(Bill entity) {
        XJdbc.executeUpdate(updateSql,
                entity.getUsername(),
                entity.getCardId(),
                entity.getCheckin(),
                entity.getCheckout(),
                entity.getStatus(),
                entity.getId()
        );
    }

    @Override
    public void deleteById(Long id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<Bill> findAll() {
        return XQuery.getEntityList(Bill.class, findAllSql);
    }

    @Override
    public Bill findById(Long id) {
        return XQuery.getSingleBean(Bill.class, findByIdSql, id);
    }

    @Override
    public List<Bill> findByUsername(String username) {
        return XQuery.getEntityList(Bill.class, findByUsernameSql, username);
    }

    @Override
    public List<Bill> findByCardId(Integer cardId) {
        return XQuery.getEntityList(Bill.class, findByCardIdSql, cardId);
    }
}
