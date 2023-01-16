package app.rest;

import app.models.Batch.Batch;
import app.models.Order.OrderLine;
import app.repositories.Batch.BatchRepository;
import app.repositories.DataLoader;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Import(DataLoader.class)
public class BatchControllerTest {
    @Autowired
    private MockMvc mvc;
    @Mock
    private OrderLine orderLine;
    @InjectMocks
    @Autowired
    private BatchController batchController;
    @Autowired
    CommandLineRunner dataLoader;
    @Autowired
    private TestRestTemplate restTemplate;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired
    private OrderlineRepository orderlineRepository;
    @Autowired
    private BatchRepository batchRepository;

    ArrayList<Batch> batchList;

    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        this.batchList = (ArrayList<Batch>) this.batchRepository.findAll();
        System.out.println(this.batchList);
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    /**
     * Checks if batchList has more than zero batches.
     *
     * @author Dia Fortmeier
     */
    @Test
    public void BatchListHasMoreThenZeroBatches() {
        Assertions.assertTrue(this.batchList.size() > 0);
    }

    /**
     * Checks if getAllBatches"/batch"
     *
     * @throws Exception if response is not ok
     * @author Dia Fortmeier
     */
    @Test
    public void GetAllBatchesResponseEntityReturnsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/batch") // Calls the /batch endpoint
                        .accept(MediaType.APPLICATION_JSON)) // Only accepts Json as a response
                .andDo(print()) // Print the result
                .andExpect(status().isOk()); // Expects that the response is ok
    }

    /**
     * Gets the batch by its ID, and checks if the resulting batch has the given id.
     *
     * @throws Exception if response is not ok or if the id does not match the expectedValue.
     * @author Dia Fortmeier
     */
    @Test
    public void GetBatchOneById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/batch/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
}
