package nl.hva.backend.models.Dashboard;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Graph {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    int orderDate;

    public Graph(){}

    public Graph(int orderDate){
        this.orderDate = orderDate;
    }

    public int getOrderCreated() {
        return orderDate;
    }

    public void setOrderCreated(int orderDate) {
        this.orderDate = orderDate;
    }
}
