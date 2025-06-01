package poly.cafe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import poly.cafe.util.XJdbc;

/**
 * API truy vấn và thao tác dữ liệu cơ bản
 *
 * @param <T> kiểu thực thể
 * @param <ID> kiểu trường khóa của thực thể
 * @author Poly
 * @version 1.0
 */
public interface CrudDAO<T, ID> {

    /**
     * Thêm mới thực thể
     *
     * @param entity thực thể chứa dữ liệu cần thêm mới
     * @return Thực thể sau khi đã thêm mới
     */
    T create(T entity);

    /**
     * Cập nhật thực thể
     *
     * @param entity thực thể chứa dữ liệu cần cập nhật
     */
    void update(T entity);

    /**
     * Xóa thực thể theo khóa chính
     *
     * @param id mã thực thể cần xóa
     */
    void deleteById(ID id);

    /**
     * Truy vấn tất cả các thực thể
     *
     * @return Kết quả truy vấn
     */
    List<T> findAll();

    /**
     * Truy vấn thực thể theo id
     *
     * @param id mã thực thể cần truy vấn
     * @return Kết quả truy vấn
     */
    T findById(ID id);
}