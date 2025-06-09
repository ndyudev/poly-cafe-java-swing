package poly.cafe.ui;

public interface HistoryController {

    void open(); // hiển thị bill theo khoảng thời gian Hôm nay 

    void fillBills(); // tải và hiển thị bill theo khoảng thời gian lọc 

    void showBillJDialog(); // mở cửa số phiếu bán hàng 

    void selectTimeRange(); // chọn khoảng thời gian 
}
