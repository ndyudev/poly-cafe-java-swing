package poly.cafe.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Bills")
public class Bill {

    @Id
    @Column(name = "Id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "Username", nullable = false, foreignKey = @ForeignKey(name = "FK_Bills_Users"))
    private Users user;

    @ManyToOne
    @JoinColumn(name = "CardId", nullable = false, foreignKey = @ForeignKey(name = "FK_Bills_Cards"))
    private Cards card;

    @Column(name = "Checkin", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkin;

    @Column(name = "Checkout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkout;

    @Column(name = "Status", nullable = false)
    private int status;

    // Constructors
    public Bill() {}

    public Bill(Users user, Cards card, Date checkin, Date checkout, int status) {
        this.user = user;
        this.card = card;
        this.checkin = checkin;
        this.checkout = checkout;
        this.status = status;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Cards getCard() {
        return card;
    }

    public void setCard(Cards card) {
        this.card = card;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bills{" +
                "id=" + id +
                ", user=" + user +
                ", card=" + card +
                ", checkin=" + checkin +
                ", checkout=" + checkout +
                ", status=" + status +
                '}';
    }
}
