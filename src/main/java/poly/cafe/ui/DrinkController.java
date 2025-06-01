package poly.cafe.ui;

import poly.cafe.entity.Bill;

public interface DrinkController {
    void setBill(Bill bill);
    void open();
    void fillCategories();
    void fillDrinks();
    void addDrinkToBill();
}
