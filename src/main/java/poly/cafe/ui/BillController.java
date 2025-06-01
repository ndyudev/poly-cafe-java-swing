package poly.cafe.ui;

import poly.cafe.entity.Bill;

public interface BillController {
    void setBill(Bill bill);
    void open();
    void close();
    void showDrinkJDialog();
    void removeDrinks();
    void updateQuantity();
    void checkout();
    void cancel();
}
