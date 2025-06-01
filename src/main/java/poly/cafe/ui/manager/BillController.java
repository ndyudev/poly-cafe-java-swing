package poly.cafe.ui.manager;

import poly.cafe.entity.Bill;

public interface BillController extends CrudController<Bill>{
    void fillBillDetails();
    void selectTimeRange();
}

