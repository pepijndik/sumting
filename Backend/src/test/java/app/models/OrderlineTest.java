package app.models;

import app.models.Order.OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderlineTest {

    OrderLine orderline1, orderline2;

    @BeforeEach
    void setup(){
        this.orderline1 = new OrderLine();
    }
}
