package poly.cafe.dao;

import java.util.List;
import poly.cafe.entity.Drink;

public interface DrinkDAO extends CrudDAO<Drink, String>{
    /**
     * Truy vấn theo loại
     * 
     * @param categoryId mã loại đồ uống
     * @return kết quả truy vấn
     */
    List<Drink> findByCategoryId(String categoryId);
}
