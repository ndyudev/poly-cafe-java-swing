/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cafe.dao;

import java.util.List;
import poly.cafe.entity.Drink;

/**
 *
 * @author Chauu Nhat Duyy
 */
public interface DrinkDAO extends CrudDAO<Drink, String> {

    List<Drink> findByCategoryId(String categoryId);

}
