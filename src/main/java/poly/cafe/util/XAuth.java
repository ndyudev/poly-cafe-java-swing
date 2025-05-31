package poly.cafe.util;

import poly.cafe.entity.User;

public class XAuth {

    // Biến user lưu thông tin người dùng hiện tại
    public static User user = User.builder()
            .Username("ndyudev") // Tên đăng nhập
            .Password("ndyudev227") // Mật khẩu
            .Enabled(true) // Trạng thái hoạt động
            .Manager(true) // Vai trò quản lý
            .Fullname("Chauu Nhat Duyy") // Họ tên
            .Photo("src/main/java/poly/cafe/icons/avatar.jpg") // Đường dẫn tương đối đến file avatar.jpg trong thư mục icons
            .build(); // Biến user này sẽ được thay thế sau khi đăng nhập

    // Kiểm tra xem có người dùng đang đăng nhập không
    public static boolean isAuthenticated() {
        return user != null;
    }

    // Kiểm tra xem người dùng có phải quản lý không
    public static boolean isManager() {
        return isAuthenticated() && user.isManager();
    }

    // Cập nhật thông tin người dùng sau khi đăng nhập
    public static void setUser(User newUser) {
        user = newUser;
    }

    // Xóa thông tin người dùng (đăng xuất)
    public static void clearUser() {
        user = null;
    }
}