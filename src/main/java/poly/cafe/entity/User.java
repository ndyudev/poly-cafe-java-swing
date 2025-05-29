package poly.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    private String Username;
    private String Password;
    private boolean Enabled;
    private String Fullname;
    @Builder.Default
    private String Photo = "photo.png";
    private boolean Manager;
}