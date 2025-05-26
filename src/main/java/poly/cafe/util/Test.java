package poly.cafe.util;

import poly.cafe.entity.Drink;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        try {
            XJdbc.openConnection();

//            testInsertDrink();
//            testGetAllDrinks();
//            testGetDrinkById();
//            testUpdateDrink();
//            testDeleteDrink();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void testInsertDrink() {
        System.out.println(">> Tạo đối tượng đồ uống mới...");
        Drink drink = new Drink();
        drink.setId("D999");
        drink.setName("Trà Đào Cam Sả");
        drink.setUnitPrice(35000);
        drink.setDiscount(0.1);
        drink.setImage("tra_dao.jpg");
        drink.setAvailable(true);
        drink.setCategoryId("CAT01");

        System.out.println(">> Insert vào DB...");
        DatabaseSQL.insertDrink(drink);
    }

    static void testGetAllDrinks() {
        System.out.println(">> In danh sách đồ uống:");
        List<Drink> drinks = DatabaseSQL.getAllDrinks();
        for (Drink d : drinks) {
            System.out.println("• " + d.getId() + " | " + d.getName());
        }
    }

    static void testGetDrinkById() {
        System.out.println(">> Lấy đồ uống theo Id D999:");
        Drink d = DatabaseSQL.getDrinkById("D999");
        if (d != null) {
            System.out.println(d.getId() + " | " + d.getName() + " | Giá: " + d.getUnitPrice());
        } else {
            System.out.println("Không tìm thấy đồ uống D999");
        }
    }

    static void testUpdateDrink() {
        System.out.println(">> Update đồ uống D999:");
        Drink d = DatabaseSQL.getDrinkById("D999");
        if (d != null) {
            d.setName("Trà Đào Cam Sả - Update");
            d.setUnitPrice(40000);
            DatabaseSQL.updateDrink(d);
            System.out.println("Update thành công");
        } else {
            System.out.println("Không tìm thấy đồ uống để update");
        }
    }

    static void testDeleteDrink() {
        System.out.println(">> Xóa đồ uống D999:");
        DatabaseSQL.deleteDrink("D999");
        System.out.println("Xóa thành công");
    }
}
