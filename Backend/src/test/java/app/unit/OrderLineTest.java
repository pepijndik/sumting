package app.unit;

import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.models.User.User;
import app.repositories.DataLoader;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderRepository;
import app.repositories.Order.OrderTypeRepository;
import app.repositories.Order.OrderlineRepository;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Import(DataLoader.class)
public class OrderLineTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    CommandLineRunner dataLoader;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderlineRepository OrderLineRepository;

    private List<OrderLine> orderLines;
    @BeforeEach

    void setup() throws Exception {
        this.dataLoader.run(null);
        this.orderLines = (List<OrderLine>)this.OrderLineRepository.findAll();
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void canRetrieveAllLines() {
        ResponseEntity<OrderLine[]> response = restTemplate.getForEntity(servletContextPath + "/orders/orderlines", OrderLine[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderLine[] responseBody = response.getBody();
        assert responseBody != null;
        assertThat("The total lines must be "+this.orderLines.size()+" but is "+ responseBody.length, responseBody.length == this.orderLines.size());
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void canRetrieveALineThatBelongsToAOther() {
        ResponseEntity<OrderLine[]> response = restTemplate.getForEntity(servletContextPath + "/orders/orderlines?orderId=1", OrderLine[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderLine[] responseBody = response.getBody();
        assert responseBody != null;

        List<OrderLine> l =  this.orderLines.stream().filter(line -> line.getOrder() != null && line.getOrder().getId() == 1).toList();
        assertThat("The total lines must be "+l.size()+" but is "+ responseBody.length, responseBody.length == l.size());
    }
}
