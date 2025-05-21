package poly.cafe.dao;

import java.util.List;
import poly.cafe.entity.User;

public interface UserDAO extends CrudDAO<User, String>{
    List<User> findByFullname(String fullName);
}
