package poly.cafe.ui.manager;

import poly.cafe.entity.Drink;

public interface DrinkController extends CrudController<Drink>{
    void fillCategories();
    void chooseFile();
}

