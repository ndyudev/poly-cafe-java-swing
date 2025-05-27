package poly.cafe.dao;

import java.util.List;
import poly.cafe.entity.Card;

public interface CardDAO extends CrudDAO<Card, Integer>{
    void create(Card entity);

    void update(Card entity);

    void deleteById(Integer id);

    List<Card> findAll();

    Card findById(Integer id);
}
