package app.repositories;

import app.models.Batch.Batch;
import app.models.Country;
import app.models.Dashboard.Graph;
import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.models.Order.OrderType;
import app.models.Project.Project;
import app.models.Project.ProjectType;
import app.models.User.User;
import app.repositories.Batch.BatchRepository;
import app.repositories.Interfaces.ProjectRepository;
import app.repositories.Interfaces.ProjectTypeRepository;
import app.repositories.Order.OrderRepository;
import app.repositories.Order.OrderTypeRepository;
import app.repositories.Order.OrderlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.util.ProxyUtils.getUserClass;

/**
 * Loads dummy data into the mem database.
 */
@Profile("test")
@Component
public class DataLoader implements CommandLineRunner {

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("Running CommandLine Startup");
        this.createCountries();
        this.createInitialUsers(10);
        this.createProjects();
        this.createOrderTypes();
        this.createOrders();
        this.createOrderLines();
        this.createOrderMonths();
        this.createBatch();
        System.out.println("Injected accounts from " + (this.userRepository != null ? getUserClass(this.userRepository.getClass()).getName() : "none"));
    }

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Creates a list of countries.
     */
    private void createCountries() {
        this.countryRepository.save(new Country(1, "Netherlands", "NL", "NLD", "https://sumting.s3.eu-west-2.amazonaws.com/flags/nl.png"));
        this.countryRepository.save(new Country(2, "Iceland", "IS", "ISL", "https://sumting.s3.eu-west-2.amazonaws.com/flags/is.png"));
        this.countryRepository.save(new Country(3, "Hong Kong", "HK", "HKG", "https://sumting.s3.eu-west-2.amazonaws.com/flags/hk.png"));

    }

    @Autowired
    private JPAUserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTypeRepository projectRepositoryType;
    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BatchRepository batchRepository;

    /**
     * Adds order-types to the order-type repository.
     */
    private void createOrderTypes() {
        this.orderTypeRepository.save(new OrderType(1, "imported by django", "stripe_contribution"));
        this.orderTypeRepository.save(new OrderType(2, "business direct order", "b2b_contribution"));
    }

    /**
     * Adds project-types to the project-type repository.
     * Also adds projects to the project repository
     */
    private void createProjects() {
        this.projectRepositoryType.save(new ProjectType(1, "imported by django"));
        this.projectRepositoryType.save(new ProjectType(2, "stripe_contribution"));

        this.projectRepository.save(new Project(1,
            "test",
            "test_long",
            "100.001.",
            "100.001."
        ));
    }

    /**
     * Adds orders to the order repository.
     */
    private void createOrders() {
        if (this.orderTypeRepository.findById(1).isPresent() && this.projectRepository.findById(1).isPresent()) {
            Order order = new Order(
                1,
                LocalDate.now(),
                "test order",
                100.00,
                "EUR",
                this.userRepository.findById(1),
                this.orderTypeRepository.findById(1).get(),
                this.projectRepository.findById(1).get());
            order.addOrderLine(new OrderLine());
            this.orderRepository.save(order);
        }

    }

    /**
     * Adds a batch to the batch repository.
     *
     * @author Dia Fortmeier
     */
    private void createBatch() {
        if (this.projectRepository.findById(1).isPresent()) {
            List<OrderLine> orderLineList = new ArrayList<>();
            OrderLine orderLine = new OrderLine();
            orderLineList.add(orderLine);

            Batch batch = new Batch(
                    1,
                    LocalDateTime.now(),
                    "Test batch",
                    1,
                    1
            );

            orderLine.setBatch(batch);
            this.orderlineRepository.save(orderLine);
            batch.setOrderLines(orderLineList);
            this.batchRepository.save(batch);
        }
    }

    /**
     * Adds users to the user repository.
     *
     * @param amount The amount of users to add.
     */
    private void createInitialUsers(int amount) {
        // check whether the repo is empty
        List<User> userList = (List<User>) this.userRepository.findAll();
        if (userList.size() > 0) return;
        System.out.println("Configuring some initial accounts in the repository");
        User admin = User.buildRandom("admin");
        userList.add(this.userRepository.save(admin));
        for (int i = 0; i < amount; i++) {
            User u = null;
            u = User.buildRandom();
            userList.add(this.userRepository.save(u));
        }
        for (User a : userList) {
            a.setEncodedPassword(a.hashPassword("Test123!"));
            System.out.println("Added user: " + a + " (initial password = 'Test123!')" + (a.validateEncodedPassword("Test123!") ? " (password is valid)" : " (password is invalid)"));
        }
    }

    @Autowired
    private OrderlineRepository orderlineRepository;

    private void createOrderLines(){
        this.orderlineRepository.save(OrderLine.buildRandom());
        this.orderlineRepository.save(OrderLine.buildRandom());
        this.orderlineRepository.save(OrderLine.buildRandom());
        this.orderlineRepository.save(OrderLine.buildRandom());
        this.orderlineRepository.save(OrderLine.buildRandom());
        this.orderlineRepository.save(OrderLine.buildRandom());
    }

    @Autowired
    private DashboardRepository dashboardRepository;

    public void createOrderMonths(){
        this.dashboardRepository.save(Graph.buildRandom());
        this.dashboardRepository.save(Graph.buildRandom());
        this.dashboardRepository.save(Graph.buildRandom());
        this.dashboardRepository.save(Graph.buildRandom());
        this.dashboardRepository.save(Graph.buildRandom());

    }

}
