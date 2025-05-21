package poly.cafe.util;

import java.util.*;
import poly.cafe.entity.*;
import static poly.cafe.util.XJdbc.openConnection;

public class DatabaseSQL {
    
    public static void main(String[] args) {
        openConnection();
    }
    // ------------------ DRINKS ------------------

    // SELECT ALL DRINKS
    public static List<Drink> getAllDrinks() {
        String sql = "SELECT * FROM Drinks";
        return XJdbc.getBeanList(Drink.class, sql);
    }

    // SELECT DRINK BY ID
    public static Drink getDrinkById(String id) {
        String sql = "SELECT * FROM Drinks WHERE Id = ?";
        return XJdbc.getSingleBean(Drink.class, sql, id);
    }

    // SELECT DRINKS BY CATEGORY
    public static List<Drink> getDrinksByCategory(String categoryId) {
        String sql = "SELECT * FROM Drinks WHERE CategoryId = ?";
        return XJdbc.getBeanList(Drink.class, sql, categoryId);
    }

    // INSERT DRINK
    public static void insertDrink(Drink drink) {
        String sql = "INSERT INTO Drinks (Id, Name, UnitPrice, Discount, Image, Available, CategoryId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        XJdbc.update(sql,
                drink.getId(),
                drink.getName(),
                drink.getUnitPrice(),
                drink.getDiscount(),
                drink.getImage(),
                drink.isAvailable(),
                drink.getCategoryId()
        );
    }

    // UPDATE DRINK
    public static void updateDrink(Drink drink) {
        String sql = "UPDATE Drinks SET Name = ?, UnitPrice = ?, Discount = ?, Image = ?, Available = ?, CategoryId = ? WHERE Id = ?";
        XJdbc.update(sql,
                drink.getName(),
                drink.getUnitPrice(),
                drink.getDiscount(),
                drink.getImage(),
                drink.isAvailable(),
                drink.getCategoryId(),
                drink.getId()
        );
    }

    // DELETE DRINK
    public static void deleteDrink(String id) {
        String sql = "DELETE FROM Drinks WHERE Id = ?";
        XJdbc.update(sql, id);
    }

    // ------------------ CATEGORIES ------------------

    // SELECT ALL CATEGORIES
    public static List<Category> getAllCategories() {
        String sql = "SELECT * FROM Categories";
        return XJdbc.getBeanList(Category.class, sql);
    }

    // SELECT CATEGORY BY ID
    public static Category getCategoryById(String id) {
        String sql = "SELECT * FROM Categories WHERE Id = ?";
        return XJdbc.getSingleBean(Category.class, sql, id);
    }

    // ------------------ CARDS ------------------

    // SELECT ALL CARDS
    public static List<Card> getAllCards() {
        String sql = "SELECT * FROM Cards";
        return XJdbc.getBeanList(Card.class, sql);
    }

    // SELECT CARD BY ID
    public static Card getCardById(int id) {
        String sql = "SELECT * FROM Cards WHERE Id = ?";
        return XJdbc.getSingleBean(Card.class, sql, id);
    }
}
