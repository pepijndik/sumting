package app.unit;

import app.models.Order.Order;
import app.models.Order.OrderType;
import app.models.User.User;
import app.repositories.DataLoader;
import app.repositories.Order.OrderTypeRepository;
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
public class OrderTypeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired
    CommandLineRunner dataLoader;
    @Autowired
    private OrderTypeRepository orderTypeRepository;
    private List<OrderType> orderTypes;

    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        this.orderTypes = (List<OrderType>) this.orderTypeRepository.findAll();
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }
    @Test
    @org.junit.jupiter.api.Order(1)
    public void canRetrieveAllOrderTypes() {
        ResponseEntity<OrderType[]> response = restTemplate.getForEntity(servletContextPath + "/orders/types", OrderType[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderType[] responseBody = response.getBody();
        assert responseBody != null;
        assertThat("The total Types must be "+this.orderTypes.size()+" but is "+ responseBody.length, responseBody.length == this.orderTypes.size());
    }
}
