package app.unit;

import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.models.User.User;
import app.repositories.DataLoader;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderTypeRepository;
import app.response.LoginResponse;
import app.rest.AuthenticationControllerTests;
import org.joda.time.DateTime;
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

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(DataLoader.class)
public class CreateOrderTest {

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

        public OrderLine[] orderLines;

        public CreateOrder(String date, String description, Integer transactionTotal, Integer typeKey, Integer payerKey, String currency, OrderLine[] orderLines) {
            this.order_date = date;
            this.description = description;
            this.transactionTotal = transactionTotal;
            this.typeKey = typeKey;
            this.payerKey = payerKey;
            this.currency = currency;
            this.orderLines = orderLines;
        }
    }

    @Test
    public void canCreateOrder() {

        assert(userList.size() > 0);
        assert(orderTypeRepository.findAll().spliterator().getExactSizeIfKnown() > 0);
        OrderLine[] orderLines = new OrderLine[1];
        //Create a new order
        CreateOrderTest.CreateOrder createOrder = new CreateOrder(
                LocalDateTime.now().toLocalDate().toString(),
                "Test Order",
                100,
                1,
                userList.get(0).getId(),
                "EUR",
                orderLines
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
