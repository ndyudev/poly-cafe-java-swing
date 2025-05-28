package poly.cafe.ui;

import java.awt.Window;
import javax.swing.JFrame;
import poly.cafe.util.XDialog;

public interface PolyCafeController {

    void init();

    default void exit() {
        if (XDialog.confirm("Bạn muốn kết thúc?")) {
            System.exit(0);
        }
    }

    default void showWindow(Window window) {
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    // Phương thức chung để mở các cửa sổ (không quy định cụ thể JFrame hay JDialog)
    default void showMainWindow(JFrame frame) {
        showWindow(frame);
    }

    default void showLoginWindow(JFrame frame) {
        showWindow(frame);
    }

    default void showWelcomeWindow(JFrame frame) {
        showWindow(frame);
    }

    // Loại bỏ các phương thức cụ thể cho JDialog (như showSalesJDialog, showChangePasswordJDialog, v.v.)
    // Thay bằng các phương thức chung nếu cần
    default void showManagerWindow(JFrame frame) {
        showWindow(frame);
    }
}