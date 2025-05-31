package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.dao.CategoryDAO;
import poly.cafe.entity.Category;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;

public class CategoryDAOImpl implements CategoryDAO {

    String createSql = "INSERT INTO Categories (Id, Name) VALUES (?, ?)";
    String updateSql = "UPDATE Categories SET Name = ? WHERE Id = ?";
    String deleteSql = "DELETE FROM Categories WHERE Id = ?";
    String deleteAllSql = "DELETE FROM Categories";
    String findAllSql = "SELECT * FROM Categories";
    String findByIdSql = "SELECT * FROM Categories WHERE Id = ?";
    String checkDrinksSql = "SELECT COUNT(*) FROM Drinks WHERE CategoryId IN (SELECT Id FROM Categories)";

    @Override
    public Category create(Category entity) {
        if (entity == null || entity.getId() == null || entity.getId().trim().isEmpty() || 
            entity.getName() == null || entity.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Entity hoặc Id/Name không được null hoặc rỗng");
        }
        Object[] values = { entity.getId(), entity.getName() };
        try {
            XJdbc.executeUpdate(createSql, values);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo Category: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Category entity) {
        if (entity == null || entity.getId() == null || entity.getId().trim().isEmpty() || 
            entity.getName() == null || entity.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Entity hoặc Id/Name không được null hoặc rỗng");
        }
        Object[] values = { entity.getName(), entity.getId() };
        try {
            XJdbc.executeUpdate(updateSql, values);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật Category: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Id không được null hoặc rỗng");
        }
        try {
            int drinkCount = ((Number) XJdbc.getValue("SELECT COUNT(*) FROM Drinks WHERE CategoryId = ?", id)).intValue();
            if (drinkCount > 0) {
                throw new IllegalStateException("Không thể xóa danh mục vì còn " + drinkCount + " đồ uống liên kết!");
            }
            XJdbc.executeUpdate(deleteSql, id);
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa Category: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            // Kiểm tra ràng buộc khóa ngoại
            int drinkCount = ((Number) XJdbc.getValue(checkDrinksSql)).intValue();
            if (drinkCount > 0) {
                // Với ON DELETE CASCADE, có thể bỏ kiểm tra này nếu bạn chấp nhận xóa cả Drinks
                System.out.println("Cảnh báo: " + drinkCount + " đồ uống sẽ bị xóa do ràng buộc CASCADE.");
            }
            XJdbc.executeUpdate(deleteAllSql);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa toàn bộ Category: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Category> findAll() {
        try {
            return XQuery.getBeanList(Category.class, findAllSql);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi truy vấn danh sách Category: " + e.getMessage(), e);
        }
    }

    @Override
    public Category findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Id không được null hoặc rỗng");
        }
        try {
            return XQuery.getSingleBean(Category.class, findByIdSql, id);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi truy vấn Category theo Id: " + e.getMessage(), e);
        }
    }
}