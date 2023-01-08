package app.rest;

import app.models.Dashboard.Graph;
import app.models.Project.Project;
import app.models.User.User;
import app.repositories.DashboardRepository;
import app.repositories.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
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

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Import(DataLoader.class)
public class DashboardControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    CommandLineRunner dataLoader;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired
    private DashboardController dashboardController;
    @Autowired
    private DashboardRepository dashboardRepository;

    List<Graph> allOrderMonths;

    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        this.allOrderMonths = (List<Graph>) this.dashboardRepository.findAll();
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    @Test
    public void FindAllReturnsAllOrders() {
        assertNotNull(this.allOrderMonths);
    }

    @Test
    public void GetAllOrderDatesByMonth(){
        java.sql.Date currentDate = Date.valueOf(LocalDate.now());
        ArrayList<Graph> response = (ArrayList<Graph>) this.dashboardRepository.findByMonth(currentDate);

        assertNotNull(response);
    }

    @Test
    public void GetAllProjectDescriptions(){
        ArrayList<String> projects = (ArrayList<String>) this.dashboardRepository.findAllDescriptions();

        assertNotNull(projects);
    }
}
