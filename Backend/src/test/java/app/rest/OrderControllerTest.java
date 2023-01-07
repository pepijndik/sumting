package app.rest;

import app.models.Order.OrderLine;
import app.repositories.DataLoader;
import app.repositories.Order.OrderlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Import(DataLoader.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;
    @Mock
    private OrderLine orderLine;
    @InjectMocks
    private OrderController orderController;
    @Autowired
    CommandLineRunner dataLoader;
    @Autowired
    private TestRestTemplate restTemplate;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired
    private OrderlineRepository orderlineRepository;


    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    /**
     * Author: Kaan Ugur
     * Description: Tests if all orders are returned
     */
    @Test
    public void FindAllReturnsAllOrders() {
        ArrayList<OrderLine> allOrders = (ArrayList<OrderLine>) this.orderlineRepository.findAll();
        assertNotNull(allOrders);
    }

    /**
     * Author: Kaan Ugur
     * Description: Checks if "/orders" return an ok response
     * @throws Exception if response is not ok
     */
    @Test
    public void ResponseEntityReturnsOk() throws Exception {
        // UnknownContentTypeException
        mvc.perform(MockMvcRequestBuilders
                        .get("/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void EditAnOrderLine(){
        // test order line
        OrderLine orderLineDummy = OrderLine.buildRandom();
        LocalDateTime updatedLoadedDate = LocalDateTime.of(2022, 2, 22, 14, 12);

        OrderLine requestBody = new OrderLine("This is an updated Note", 22.0,
                "Proof name updated", 31.0, 32.0, "Small proof updated",
                "Medium proof updated", "Large proof updated", 12.0,
                12.0, updatedLoadedDate);

//        OrderLine updateOrderLine = orderController.editOrder(orderLineDummy.getId(), requestBody);

//        System.out.println(updateOrderLine);

    }

}
