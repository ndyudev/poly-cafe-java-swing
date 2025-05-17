package poly.cafe.entity;

import javax.persistence.*;

@Entity
@Table(name = "Cards")
public class Cards {

    @Id
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Status", nullable = false)
    private int status;

    // Constructors
    public Cards() {}

    public Cards(int id, int status) {
        this.id = id;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cards{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
