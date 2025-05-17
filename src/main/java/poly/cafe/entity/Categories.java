package poly.cafe.entity;

import javax.persistence.*;

@Entity
@Table(name = "Categories")
public class Categories {

    @Id
    @Column(name = "Id", length = 20, nullable = false)
    private String id;

    @Column(name = "Name", length = 50, nullable = false)
    private String name;

    // Constructors
    public Categories() {}

    public Categories(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}