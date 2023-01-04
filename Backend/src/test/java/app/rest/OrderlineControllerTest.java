package app.rest;

import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.models.User.User;
import app.repositories.DataLoader;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderRepository;
import app.repositories.Order.OrderlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(DataLoader.class)
public class OrderlineControllerTest {

    @Autowired
    CommandLineRunner dataLoader;

    @Autowired
    private TestRestTemplate restTemplate;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired // injects an implementation of BooksRepository here.
    private OrderRepository orderRepository;
    private OrderlineRepository orderlineRepository;

    private List<Order> orderlineList;

    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        this.orderlineList = (List<Order>) this.orderRepository.findAll();
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    @Test
    public void FindAllReturnsAll() {
        // check Users have been loaded
        assertTrue(this.orderlineList.size() > 0);
    }
}
