package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.dao.CardDAO;
import poly.cafe.entity.Card;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;

public class CardDAOImpl implements CardDAO {

    private final String createSql = "INSERT INTO Cards(Id, Status) VALUES(?, ?)";
    private final String updateSql = "UPDATE Cards SET Status=? WHERE Id=?";
    private final String deleteByIdSql = "DELETE FROM Cards WHERE Id=?";
    
    private final String findAllSql = "SELECT * FROM Cards";
    private final String findByIdSql = findAllSql + " WHERE Id=?";

    @Override
    public Card create(Card entity) {
        Object[] values = {
            entity.getId(),
            entity.getStatus()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
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
        XJdbc.executeUpdate(deleteByIdSql, id);
    }

    @Override
    public List<Card> findAll() {
        return XQuery.getBeanList(Card.class, findAllSql);
    }

    @Override
    public Card findById(Integer id) {
        return XQuery.getSingleBean(Card.class, findByIdSql, id);
    }
}