package poly.cafe.entity;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class Users {

    @Id
    @Column(name = "Username", length = 20, nullable = false)
    private String username;

    @Column(name = "Password", length = 50, nullable = false)
    private String password;

    @Column(name = "Enabled", nullable = false)
    private boolean enabled;

    @Column(name = "Fullname", length = 50, nullable = false)
    private String fullname;

    @Column(name = "Photo", length = 50, nullable = false)
    private String photo;

    @Column(name = "Manager", nullable = false)
    private boolean manager;

    // Constructors
    public Users() {}

    public Users(String username, String password, boolean enabled, String fullname, String photo, boolean manager) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.fullname = fullname;
        this.photo = photo;
        this.manager = manager;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", fullname='" + fullname + '\'' +
                ", photo='" + photo + '\'' +
                ", manager=" + manager +
                '}';
    }
}
