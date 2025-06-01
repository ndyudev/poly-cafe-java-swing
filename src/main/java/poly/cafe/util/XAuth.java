package poly.cafe.util;

import poly.cafe.entity.User;

public class XAuth {
    public static User user = User.builder()
            .username("chauunhatduyyit@gmail.com")
            .password("ndyudev227")
            .enabled(true)
            .manager(true)
            .fullname("Châu Nhật Duy")
            .photo("dyudev.png")
            .build();
}