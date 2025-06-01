package poly.cafe.dao;

import java.util.Date;
import java.util.List;
import poly.cafe.entity.Bill;

public interface BillDAO extends CrudDAO<Bill, Long>{
    /**
     * Truy vấn theo nhân viên
     * 
     * @param username tên đăng nhập của nhân viên
     * @return kết quả truy vấn
     */
    public List<Bill> findByUsername(String username);
    /**
     * Truy vấn theo thẻ định danh
     * 
     * @param cardId mã thẻ định danh
     * @return kết quả truy vấn
     */
    public List<Bill> findByCardId(Integer cardId);
    /**
     * Truy vấn đang phục vụ của thẻ
     * 
     * @param cardId mã thẻ định danh
     * @return kết quả truy vấn
     */
    public Bill findServicingByCardId(Integer cardId);
    /**
     * Truy vấn theo khoảng thời gian
     * 
     * @param from thời gian bắt đầu truy vấn
     * @param to  thời gian kết thúc truy vấn
     * @return kết quả truy vấn
     */
    public List<Bill> findByTimeRange(Date from, Date to);
    /**
     * Truy vấn theo khoảng thời gian
     * 
     * @param username tên đăng nhập của nhân viên
     * @param from thời gian bắt đầu truy vấn
     * @param to  thời gian kết thúc truy vấn
     * @return kết quả truy vấn
     */
    public List<Bill> findByUserAndTimeRange(String username, Date from, Date to);
}
