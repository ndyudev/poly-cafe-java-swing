package poly.cafe.dao;

import poly.cafe.entity.User;
import java.util.List;

public interface UserDAO {

    void insert(User user);

    void update(User user);

    void delete(String username);

    List<User> findAll();

    User findById(String username);

    List<User> findByName(String keyword);
}
