package poly.cafe.dao.impl;

import java.util.Date;
import java.util.List;
import poly.cafe.entity.Bill;
import poly.cafe.util.XJdbc;
import poly.cafe.dao.BillDAO;
import poly.cafe.util.XAuth;
import poly.cafe.util.XQuery;

public class BillDAOImpl implements BillDAO {

    private final String createSql = "INSERT INTO Bills(Username, CardId, Checkin, Status) VALUES(?, ?, ?, ?)";
    private final String updateSql = "UPDATE Bills SET Username=?, CardId=?, Checkin=?, Checkout=?, Status=? WHERE Id=?";
    private final String deleteByIdSql = "DELETE FROM Bills WHERE Id=?";

    private final String findAllSql = "SELECT * FROM Bills";
    private final String findByIdSql = findAllSql + " WHERE Id=?";
    private final String findLastSql = findAllSql + " WHERE Id IN(SELECT max(Id) FROM Bills)";
    private final String findByUsernameSql = findAllSql + " WHERE Username=?";
    private final String findByCardIdSql = findAllSql + " WHERE CardId=?";
    private final String findServicingByCardIdSql = findAllSql + " WHERE CardId=? AND Status=0";
    private final String findByUsernameAndTimeRangeSql = findAllSql + " WHERE Username=? AND Checkin BETWEEN ? AND ? ORDER BY Checkin DESC";
    private final String findByTimeRangeSql = findAllSql + " WHERE Checkin BETWEEN ? AND ? ORDER BY Checkin DESC";

    @Override
    public Bill create(Bill entity) {
        Object[] values = {
            entity.getUsername(),
            entity.getCardId(),
            entity.getCheckin(),
            entity.getStatus()
        };
        XJdbc.executeUpdate(createSql, values);
        return XQuery.getSingleBean(Bill.class, findLastSql);
    }

    @Override
    public void update(Bill entity) {
        Object[] values = {
            entity.getUsername(),
            entity.getCardId(),
            entity.getCheckin(),
            entity.getCheckout(),
            entity.getStatus(),
            entity.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(Long id) {
        XJdbc.executeUpdate(deleteByIdSql, id);
    }

    @Override
    public List<Bill> findAll() {
        return XQuery.getBeanList(Bill.class, findAllSql);
    }

    @Override
    public Bill findById(Long id) {
        return XQuery.getSingleBean(Bill.class, findByIdSql, id);
    }
    
    @Override
    public List<Bill> findByUsername(String username) {
        return XQuery.getBeanList(Bill.class, findByUsernameSql, username);
    }

    @Override
    public List<Bill> findByCardId(Integer cardId) {
        return XQuery.getBeanList(Bill.class, findByCardIdSql, cardId);
    }

    @Override
    public Bill findServicingByCardId(Integer cardId) {
        Bill bill = XQuery.getSingleBean(Bill.class, findServicingByCardIdSql, cardId);
        if (bill == null) {
            Bill newBill = new Bill();
            newBill.setCardId(cardId);
            newBill.setCheckin(new Date());
            newBill.setStatus(0);
            newBill.setUsername(XAuth.user.getUsername());
            bill = this.create(newBill);
        }
        return bill;
    }

    @Override
    public List<Bill> findByUserAndTimeRange(String username, Date begin, Date end) {
        return XQuery.getBeanList(Bill.class, findByUsernameAndTimeRangeSql, username, begin, end);
    }

    @Override
    public List<Bill> findByTimeRange(Date begin, Date end) {
        return XQuery.getBeanList(Bill.class, findByTimeRangeSql, begin, end);
    }
}