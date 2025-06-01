package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.entity.BillDetail;
import poly.cafe.util.XJdbc;
import poly.cafe.dao.BillDetailDAO;
import poly.cafe.util.XQuery;

public class BillDetailDAOImpl implements BillDetailDAO {

    private final String createSql = "INSERT INTO BillDetails(BillId, DrinkId, UnitPrice, Discount, Quantity) VALUES(?, ?, ?, ?, ?)";
    private final String updateSql = "UPDATE BillDetails SET BillId=?, DrinkId=?, UnitPrice=?, Discount=?, Quantity=? WHERE Id=?";
    private final String deleteByIdSql = "DELETE FROM BillDetails WHERE Id=?";

    private final String findAllSql = "SELECT bd.*, d.Name AS drinkName FROM BillDetails bd JOIN Drinks d ON d.Id=bd.DrinkId";
    private final String findByIdSql = findAllSql + " WHERE bd.Id=?";
    private final String findLastSql = findAllSql + " WHERE bd.Id IN(SELECT MAX(Id) FROM BillDetails)";
    private final String findByBillIdSql = findAllSql + " WHERE bd.BillId=?";
    private final String findByDrinkIdSql = findAllSql + " WHERE bd.DrinkId=?";

    @Override
    public BillDetail create(BillDetail entity) {
        Object[] values = {
            entity.getBillId(),
            entity.getDrinkId(),
            entity.getUnitPrice(),
            entity.getDiscount(),
            entity.getQuantity()
        };
        XJdbc.executeUpdate(createSql, values);
        return XQuery.getSingleBean(BillDetail.class, findLastSql);
    }

    @Override
    public void update(BillDetail entity) {
        Object[] values = {
            entity.getBillId(),
            entity.getDrinkId(),
            entity.getUnitPrice(),
            entity.getDiscount(),
            entity.getQuantity(),
            entity.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(Long id) {
        XJdbc.executeUpdate(deleteByIdSql, id);
    }

    @Override
    public List<BillDetail> findAll() {
        return XQuery.getBeanList(BillDetail.class, findAllSql);
    }

    @Override
    public BillDetail findById(Long id) {
        return XQuery.getSingleBean(BillDetail.class, findByIdSql, id);
    }

    @Override
    public List<BillDetail> findByBillId(Long billId) {
        return XQuery.getBeanList(BillDetail.class, findByBillIdSql, billId);
    }

    @Override
    public List<BillDetail> findByDrinkId(String drinkId) {
        return XQuery.getBeanList(BillDetail.class, findByDrinkIdSql, drinkId);
    }
}