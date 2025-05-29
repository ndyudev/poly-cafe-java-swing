package poly.cafe.util;

import poly.cafe.entity.User;

public class XAuth {

    // Biến user lưu thông tin người dùng hiện tại
    public static User user = User.builder()
            .Username("ndyudev") // Sửa thành .username()
            .Password("ndyudev227") // Sửa thành .password()
            .Enabled(true) // Sửa thành .enabled()
            .Manager(true) // Sửa thành .manager()
            .Fullname("Chauu Nhat Duyy") // Sửa thành .fullname()
            .Photo("trump.png") // Sửa thành .photo()
            .build(); // biến user này sẽ được thay thế sau khi đăng nhập

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
