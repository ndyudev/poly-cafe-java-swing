package poly.cafe.ui;

import poly.cafe.util.XDialog;

public interface LoginController {
    void open();
    void login();
    default void exit(){
        if(XDialog.confirm("Bạn muốn kết thúc?")){
            System.exit(0);
        }
    }
}
