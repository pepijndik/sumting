package app.rest;

import app.models.Order.OrderLine;
import app.repositories.DataLoader;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderRepository;
import app.repositories.Order.OrderTypeRepository;
import app.repositories.Order.OrderlineRepository;
import org.junit.jupiter.api.Assertions;
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
    @Autowired
    private OrderController orderController;
    @Autowired
    CommandLineRunner dataLoader;
    @Autowired
    private TestRestTemplate restTemplate;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired
    private OrderlineRepository orderlineRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JPAUserRepository userRepository;

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    ArrayList<OrderLine> allOrders ;

    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        this.allOrders = (ArrayList<OrderLine>) this.orderlineRepository.findAll();
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    /**
     * @author Kaan Ugur
     * Description: Tests if all orders are returned
     */
    @Test
    public void FindAllReturnsAllOrders() {
        Assertions.assertTrue(this.allOrders.size() > 0);
    }

    /**
     * @author Kaan Ugur
     * Description: Checks if "/orders" return an ok response
     *
     * @throws Exception if response is not ok
     */
    @Test
    public void ResponseEntityReturnsOk() throws Exception {
        // UnknownContentTypeException
        mvc.perform(MockMvcRequestBuilders
                        .get("/orders") // Calls the /orders endpoint
                        .accept(MediaType.APPLICATION_JSON)) // Only accepts Json as a response
                .andDo(print()) // Print the result
                .andExpect(status().isOk()); // Expects that the response is ok
    }


    /**
     * @author Kaan Ugur
     * Description: Updates an existing order line
     */
    @Test
    public void EditAnOrderLine() {
        // Created dummy data
        OrderLine orderLineDummy = new OrderLine(155, "Notes", 22.0, "proof name"
                , 45.0, 56.0, "proofSmall", "proofMedium",
                "proofLarge", 13.0, 14.0, null);
        LocalDateTime updatedLoadedDate = LocalDateTime.of(2022, 2, 22, 14, 12);

        // Updated the dummy data
        orderLineDummy.setLoadedDate(updatedLoadedDate);
        orderLineDummy.setNotes("This is an updated note");
        orderLineDummy.setProofName("This is an edited proof name");
        orderLineDummy.setProofSmall("This is an edited small proof name");
        orderLineDummy.setProofMedium("This is an edited medium proof name");
        orderLineDummy.setProofLarge("This is an edited large proof name");

        // Saved the updated dummy data
        OrderLine savedOrderLine = orderlineRepository.save(orderLineDummy);

        // Checks if the loadedDate is updated by checking it if it is not null
        assertNotNull(savedOrderLine.getLoadedDate());

        // check updated attributes
        Assertions.assertEquals("This is an updated note", savedOrderLine.getNotes());
        Assertions.assertEquals("This is an edited proof name", savedOrderLine.getProofName());
        Assertions.assertEquals("This is an edited small proof name", savedOrderLine.getProofSmall());
        Assertions.assertEquals("This is an edited medium proof name", savedOrderLine.getProofMedium());

        // Checks if the order line is actually changed
        Assertions.assertNotEquals("proofLarge", savedOrderLine.getProofLarge());
    }
}
