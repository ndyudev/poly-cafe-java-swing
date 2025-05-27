package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.dao.CardDAO;
import poly.cafe.entity.Card;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;

public class CardDAOImpl implements CardDAO {

    String createSql = "INSERT INTO Cards(Id, Status) VALUES(?, ?)";
    String updateSql = "UPDATE Cards SET Status=? WHERE Id=?";
    String deleteSql = "DELETE FROM Cards WHERE Id=?";
    String findAllSql = "SELECT * FROM Cards";
    String findByIdSql = "SELECT * FROM Cards WHERE Id=?";

    @Override
        public void create(Card entity) {
        Object[] values = {
            entity.getId(),
            entity.getStatus()
        };
        XJdbc.executeUpdate(createSql, values);
    }

    @Override
    public void update(Card entity) {
        Object[] values = {
            entity.getStatus(),
            entity.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<Card> findAll() {
        return XQuery.getEntityList(Card.class, findAllSql);
    }

    @Override
    public Card findById(Integer id) {
        return XQuery.getSingleBean(Card.class, findByIdSql, id);
    }

//    @Override
//    public List<Drink> findByCategoryId(String categoryId) {
//        return XQuery.getEntityList(Drink.class, findByCategoryId, categoryId);
//    }
}
