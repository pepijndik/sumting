package app.unit;

import app.models.Order.Order;
import app.models.User.User;
import app.repositories.DataLoader;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderTypeRepository;
import app.response.LoginResponse;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Range.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(DataLoader.class)
public class OrderTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    CommandLineRunner dataLoader;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired
    private JPAUserRepository userRepo;
    @Autowired

    private OrderTypeRepository orderTypeRepository;
    private List<User> userList;
    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        this.userList = (List<User>) this.userRepo.findAll();
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    private static class CreateOrder {
        public String order_date;
        public String description;
        public Integer transactionTotal;
        public Integer typeKey;
        public Integer payerKey;
        public String currency;



        public CreateOrder(String date, String description, Integer transactionTotal, Integer typeKey, Integer payerKey, String currency) {
            this.order_date = date;
            this.description = description;
            this.transactionTotal = transactionTotal;
            this.typeKey = typeKey;
            this.payerKey = payerKey;
            this.currency = currency;

        }
    }

    @Test
    public void canRetrieveAllOrders() {
        ResponseEntity<Order[]> response = restTemplate.getForEntity(servletContextPath + "/orders", Order[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Order[] responseBody = response.getBody();
        assert responseBody != null;
        assertThat("The order id must be 1", responseBody.length == 1);
    }

    @Test
    public void canRetrieveOneOrders() {
        ResponseEntity<Order> response = restTemplate.getForEntity(servletContextPath + "/orders/1", Order.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Order responseBody = response.getBody();
        assert responseBody != null;
        assertThat("The order id must be 1", responseBody.getId() == 1);
        assertThat("Name must be test order", responseBody.getDescription().equals("test order"));
    }
    @Test
    public void canCreateOrder() {

        assert(userList.size() > 0);
        assert(orderTypeRepository.findAll().spliterator().getExactSizeIfKnown() > 0);
        //Create a new order
        OrderTest.CreateOrder createOrder = new CreateOrder(
                LocalDateTime.now().toLocalDate().toString(),
                "Test Order",
                100,
                1,
                userList.get(0).getId(),
                "EUR"
        );
        ResponseEntity<Order> response = null;
        try {
            response = this.restTemplate.postForEntity("/orders", createOrder, Order.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assert response != null;
        // check status code, location header and response body of post request
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "The response status code should be 201, but was " + response.getStatusCode());

        // check response body
        Order order = response.getBody();
        assert order != null;
        assertEquals(createOrder.order_date, order.getOrder_date().toString(), "The order date should be " + createOrder.order_date + ", but was " + order.getOrder_date());
    }
}
