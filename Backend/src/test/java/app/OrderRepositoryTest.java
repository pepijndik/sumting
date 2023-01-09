package app;

import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.models.Order.OrderType;
import app.models.Project.Project;
import app.models.User.User;
import app.repositories.Order.OrderRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public OrderRepository orderRepository() {
            return new OrderRepository() {
                // implement methods
            };
        }
    }

    private Order GenerateOrder(Integer id, LocalDate date){
        Order order  = new Order(
                id,
                date,
                "test order",
                100.00,
                "EUR",
                new User(),
                new OrderType(1,"imported by django","stripe_contribution"),
                new Project(1,
                        "test",
                        "test_long",
                        "100.001.",
                        "100.001."
                ));
        order.addOrderLine(new OrderLine());
        return order;
    }
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void should_find_none_if_repository_is_empty() {
        Iterable orders = orderRepository.findAll();
        assertThat(orders).isEmpty();
    }

    @Test
    public void should_store_a_order() {
        LocalDate date = LocalDate.now();
        Order order  = this.GenerateOrder(1,date);
        Order savedOrder =  this.orderRepository.save(order);
        assertThat(savedOrder).hasFieldOrPropertyWithValue("currency", "EUR");
        assertThat(savedOrder).hasFieldOrPropertyWithValue("description", "test order");
        assertThat(savedOrder).hasFieldOrPropertyWithValue("transactionTotal", 100.00);
        assertThat(savedOrder).hasFieldOrPropertyWithValue("order_date", date);
    }


    @Test
    public void should_find_order_by_id() { }
    @Test
    void canPersist() throws Exception {
        this.entityManager.persist(new Order());
    }
}
