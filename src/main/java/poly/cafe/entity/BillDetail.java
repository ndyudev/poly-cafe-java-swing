package poly.cafe.entity;

import javax.persistence.*;

@Entity
@Table(name = "BillDetails")
public class BillDetail {

    @Id
    @Column(name = "Id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "BillId", nullable = false, foreignKey = @ForeignKey(name = "FK_BillDetails_Bills"))
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "DrinkId", nullable = false, foreignKey = @ForeignKey(name = "FK_BillDetails_Drinks"))
    private Drinks drink;

    @Column(name = "UnitPrice", nullable = false)
    private float unitPrice;

    @Column(name = "Discount", nullable = false)
    private float discount;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    // Constructors
    public BillDetail() {}

    public BillDetail(Bill bill, Drinks drink, float unitPrice, float discount, int quantity) {
        this.bill = bill;
        this.drink = drink;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.quantity = quantity;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Drinks getDrink() {
        return drink;
    }

    public void setDrink(Drinks drink) {
        this.drink = drink;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BillDetails{" +
                "id=" + id +
                ", bill=" + bill +
                ", drink=" + drink +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", quantity=" + quantity +
                '}';
    }
}
