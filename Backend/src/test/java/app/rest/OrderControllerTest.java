package app.rest;

import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.repositories.DataLoader;
import app.repositories.Order.OrderlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(DataLoader.class)
public class OrderlineControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    CommandLineRunner dataLoader;
    @Autowired
    private TestRestTemplate restTemplate;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired
    private OrderlineRepository orderlineRepository;

    private List<OrderLine> orderlineList;

    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    @Test
    public void FindAllReturnsAllOrders() {
        ArrayList<OrderLine> allOrders = (ArrayList<OrderLine>) this.orderlineRepository.findAll();
        assertNotNull(allOrders);

    }

    @Test
    public void ResponseEntityReturnsOk() throws Exception {
        // UnknownContentTypeException
        mvc.perform(MockMvcRequestBuilders
                        .get("/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
