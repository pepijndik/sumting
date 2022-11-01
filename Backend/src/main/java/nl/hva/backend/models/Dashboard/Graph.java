package nl.hva.backend.models.Dashboard;

import jdk.jfr.Timestamp;
import nl.hva.backend.models.Identifiable;
import nl.hva.backend.models.Order.Order;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = Order.TABLE_NAME)
public class Graph implements Identifiable<Integer> {
    public static final String TABLE_NAME = "\"order\"";

    @Id()
    @Column(name = "order_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Graph() {

    }

    public Graph(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    @Timestamp
    @Column(name = "created_at", columnDefinition = "timestamp")
    private LocalDate createdAt;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

}
