package app.unit;

import app.models.Order.Order;
import app.models.Project.Project;
import app.models.User.User;
import app.repositories.DataLoader;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderRepository;
import app.repositories.Order.OrderTypeRepository;
import app.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Autowired
    private OrderRepository orderRepository;

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
    @org.junit.jupiter.api.Order(1)
    public void canRetrieveAllOrders() {
        ResponseEntity<Order[]> response = restTemplate.getForEntity(servletContextPath + "/orders", Order[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Order[] responseBody = response.getBody();
        assert responseBody != null;
        assertThat("The total orders must be 1 but is "+ responseBody.length, responseBody.length == 1);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void canRetrieveOneOrders() {
        ResponseEntity<Order> response = restTemplate.getForEntity(servletContextPath + "/orders/1", Order.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Order responseBody = response.getBody();
        assert responseBody != null;
        assertThat("The order id must be 1", responseBody.getId() == 1);
        assertThat("There are no orderline", responseBody.getOrderLines().size() == 0);
        assertThat("Name must be test order", responseBody.getDescription().equals("test order"));
    }
    @Test
    @org.junit.jupiter.api.Order(3)
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
//    /**
//     * Can retrieve orders by user
//     */
//    @Test
//    @org.junit.jupiter.api.Order(4)
//    public void canRetrieveOrderByClientID() {
//        //Save two new orders for testing
//
//        Project project = new Project();
//        project.setId(1);
//
//        System.out.println(userList.get(0).getId());
//        System.out.println(project.getId());
//
//        if (userList.size() > 0 && orderTypeRepository.findById(1).isPresent()) {
//            Order o = new Order(
//                2,
//                LocalDateTime.now().toLocalDate(),
//                "Test Order",
//                100.0,
//                "EUR",
//                userList.get(0),
//                orderTypeRepository.findById(1).get(),
//                project
//            );
//            System.out.println(o.getId());
//            System.out.println(o.getPayer().getId());
//            orderRepository.save(o);
//        }
//
//        System.out.println(orderRepository.findAll());
//        for (Order or : orderRepository.findAll()) {
//            System.out.println(or.getId());
//            System.out.println(or.getPayer().getId());
//            System.out.println(or.getOrderType().getType());
//        }
//
//        ResponseEntity<Order> response = restTemplate.getForEntity(servletContextPath + "orders/combinedSearch?clientID=1&projectID=null", Order.class);
//        //assertEquals(HttpStatus.OK, response.getStatusCode());
////        System.out.println(response.getStatusCode());
//        System.out.println(response.getBody().getId());
//    }
}
