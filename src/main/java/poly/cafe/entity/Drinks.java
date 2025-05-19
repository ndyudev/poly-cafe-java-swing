package poly.cafe.entity;

import javax.persistence.*;

@Entity
@Table(name = "Drinks")
public class Drinks {

    @Id
    @Column(name = "Id", length = 20, nullable = false)
    private String id;

    @Column(name = "Name", length = 50, nullable = false)
    private String name;

    @Column(name = "UnitPrice", nullable = false)
    private float unitPrice;

    @Column(name = "Discount", nullable = false)
    private float discount;

    @Column(name = "Image", length = 50, nullable = false)
    private String image;

    @Column(name = "Available", nullable = false)
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false, foreignKey = @ForeignKey(name = "FK_Drinks_Categories"))
    private Category category;

    // Constructors
    public Drinks() {}

    public Drinks(String id, String name, float unitPrice, float discount, String image, boolean available, Category category) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.image = image;
        this.available = available;
        this.category = category;
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

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Drinks{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", image='" + image + '\'' +
                ", available=" + available +
                ", category=" + category +
                '}';
    }
}