package poly.cafe.dao;

import java.util.List;
import poly.cafe.entity.Category;

public interface CategoryDAO {
    Category insert(Category entity);
    void update(Category entity);
    void deleteById(String id);
    List<Category> findAll();
    Category findById(String id);
}

