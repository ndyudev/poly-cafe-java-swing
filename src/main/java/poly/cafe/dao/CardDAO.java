package poly.cafe.dao;

import java.util.List;
import poly.cafe.entity.Card;

public interface CardDAO extends CrudDAO<Card, Integer>{
    void insert(Card id);

    void update(Card id);

    void delete(Integer id);

    List<Card> findAll();

    Card findById(Integer id);

    List<Card> findByName(Integer id);
}
