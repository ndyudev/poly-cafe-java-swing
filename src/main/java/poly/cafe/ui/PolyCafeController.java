package poly.cafe.ui;

import javax.swing.JFrame;
import poly.cafe.ui.manager.BillManagerJDialog;
import poly.cafe.ui.manager.CardManagerJDialog;
import poly.cafe.ui.manager.CategoryManagerJDialog;
import poly.cafe.ui.manager.DrinkManagerJDialog;
import poly.cafe.ui.manager.RevenueManagerJDialog;
import poly.cafe.ui.manager.UserManagerJDialog;
import poly.cafe.util.XDialog;

public interface PolyCafeController {

    void init();

    default void exit() {
        if (XDialog.confirm("Bạn muốn kết thúc?")) {
            System.exit(0);
        }
    }

    // Đổi từ JDialog sang JFrame
    default void showJFrame(JFrame frame) {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    default void showLoginJDialog(JFrame frame) {
        this.showJFrame(new LoginJDialog(frame, true));
    }

    default void showSalesJDialog(JFrame parent) {
        this.showJFrame(new SalesJDialog(parent, true));
        this.dispose();
    }

    default void showHistoryJDialog(JFrame parent) {
        this.showJFrame(new HistoryJDialog(parent, true));
        this.dispose();
    }

    default void showDrinkManagerJDialog(JFrame parent) {
        this.showJFrame(new DrinkManagerJDialog(parent, true));
        this.dispose();
    }

    default void showCategoryManagerJDialog(JFrame parent) {
        this.showJFrame(new CategoryManagerJDialog(parent, true));
        this.dispose();
    }

    default void showCardManagerJDialog(JFrame parent) {
        this.showJFrame(new CardManagerJDialog(parent, true));
        this.dispose();
    }

    default void showBillManagerJDialog(JFrame parent) {
        this.showJFrame(new BillManagerJDialog(parent, true));
        this.dispose();
    }

    default void showUserManagerJDialog(JFrame parent) {
        this.showJFrame(new UserManagerJDialog(parent, true));
        this.dispose();
    }

    default void showRevenueManagerJDialog(JFrame parent) {
        this.showJFrame(new RevenueManagerJDialog(parent, true));
        this.dispose();
    }

    default void showChangePasswordJDialog(JFrame parent) {
        this.showDialog(new ChangePasswordJDialog(parent));
         this.dispose();
    }

    public void dispose();

    public void showDialog(ChangePasswordJDialog changePasswordJDialog);
}
