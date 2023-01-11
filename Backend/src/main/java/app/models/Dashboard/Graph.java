package app.models.Dashboard;

import app.models.Order.Order;
import jdk.jfr.Timestamp;
import app.models.Identifiable;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

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

    public Graph(Date createdAt) {
        this.createdAt = createdAt;
    }


    @Timestamp
    @Column(name = "created_at", columnDefinition = "timestamp")
    private Date createdAt;

    public static Graph buildRandom() {
        Graph graph = new Graph();
        Random random = new Random();
        int minDay = (int) LocalDate.of(2020, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2022, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        Date randomDate = Date.valueOf(LocalDate.ofEpochDay(randomDay));
        Date currentDate = Date.valueOf(LocalDate.now());

        graph.setCreatedAt(currentDate);

        return graph;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
