package poly.cafe.dao;

import java.util.List;
import poly.cafe.entity.BillDetail;

public interface BillDetailDAO extends CrudDAO<BillDetail, Long>{
    /**
     * Truy vấn theo phiếu bán hàng
     * 
     * @param billId mã bill
     * @return kết quả truy vấn
     */
    public List<BillDetail> findByBillId(Long billId);
    /**
     * Truy vấn theo đồ uống
     * 
     * @param drinkId mã đồ uống
     * @return kết quả truy vấn
     */
    public List<BillDetail> findByDrinkId(String drinkId);
}
