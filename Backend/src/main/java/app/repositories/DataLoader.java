package app.repositories;

import app.models.Country;
import app.models.Dashboard.Graph;
import app.models.Order.OrderLine;
import app.models.Order.OrderType;
import app.models.Project.Project;
import app.models.User.User;
import app.repositories.Order.OrderTypeRepository;
import app.repositories.Order.OrderlineRepository;
import app.repositories.Project.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.util.ProxyUtils.getUserClass;

/**
 * Loads dummy data into the mem databse.
 */
@Profile("test")
@Component
public class DataLoader implements CommandLineRunner
{
    @Override
    @Transactional
    public void run(String... args)  {
        System.out.println("Running CommandLine Startup");
        this.createCountries();
        this.createInitialUsers(10);
        this.createOrderTypes();
        this.createOrderLines();
        this.createOrderMonths();
        this.createProjects();
        System.out.println("Injected accounts from " +(this.userRepository != null ? getUserClass(this.userRepository.getClass()).getName() : "none"));
    }


    @Autowired
    private CountryRepository countryRepository;
    private void createCountries(){
        this.countryRepository.save(new Country(1,"Netherlands", "NL","NLD","https://sumting.s3.eu-west-2.amazonaws.com/flags/nl.png"));
        this.countryRepository.save(new Country(2,"Iceland", "IS","ISL","https://sumting.s3.eu-west-2.amazonaws.com/flags/is.png"));
        this.countryRepository.save(new Country(3,"Hong Kong", "HK","HKG","https://sumting.s3.eu-west-2.amazonaws.com/flags/hk.png"));

    }
    @Autowired
    private JPAUserRepository userRepository;

    @Autowired
    private OrderTypeRepository orderTypeRepository;


    private void createOrderTypes(){
        this.orderTypeRepository.save(new OrderType(1,"imported by django","stripe_contribution"));
        this.orderTypeRepository.save(new OrderType(2,"business direct order","b2b_contribution"));
    }
    private void createInitialUsers(int amount) {
        // check whether the repo is empty
        List<User> userList = (List<User>) this.userRepository.findAll();
        if (userList.size() > 0) return;
        System.out.println("Configuring some initial accounts in the repository");
        User admin=    User.buildRandom("admin");
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

    @Autowired
    private ProjectServiceImpl projectService;

    public void createProjects(){
        this.projectService.save(new Project(1, "this is an description",
                "This is an description", "Latitude", "Longitude"));
        this.projectService.save(new Project(2, "this is an description",
                "This is an description", "Latitude", "Longitude"));
        this.projectService.save(new Project(3, "this is an description",
                "This is an description", "Latitude", "Longitude"));
        this.projectService.save(new Project(4, "this is an description",
                "This is an description", "Latitude", "Longitude"));
        this.projectService.save(new Project(5, "this is an description",
                "This is an description", "Latitude", "Longitude"));
    }

}
