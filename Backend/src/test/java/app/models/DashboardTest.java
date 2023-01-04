package app.models;

import app.models.Dashboard.Graph;
import app.models.Order.OrderLine;
import app.models.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class DashboardTest {

    OrderLine orderLine1, orderLine2;

    @BeforeEach
    void setup() {
        this.orderLine1 = OrderLine.buildRandom();
        this.orderLine2 = OrderLine.buildRandom();
    }

    @Test
    void randomOrderLine(){
        System.out.println("OrderLine 1 : " + this.orderLine1.getId());
        System.out.println("OrderLine 2 : " + this.orderLine2.getId());
    }
}
