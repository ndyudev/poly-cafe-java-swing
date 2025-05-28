package poly.cafe.util;

import poly.cafe.entity.User;


public class XAuth { 
    public static User user = User.builder() 
            .Username("ndyudev") 
            .Password("ndyudev227") 
            .Enabled(true) 
            .Manager(true) 
            .Fullname("Chauu Nhat Duyy") 
            .Photo("trump.png") 
            .build(); // biến user này sẽ được thay thế sau khi đăng nhập 
}