package app.rest;

import app.models.Dashboard.Graph;
import app.models.Project.Project;
import app.repositories.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.sql.Date;


@Controller
public class DashboardController {

    private final DashboardRepository dashboardRepository;

    @Autowired
    public DashboardController(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @GetMapping("/orderMonths")
    public ResponseEntity<Iterable<Graph>> ordersPerMonth() {
        return new ResponseEntity<>(dashboardRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/orderMonths/{pastMonths}")
    public ResponseEntity<Iterable<Graph>> ordersSorted(@PathVariable Date pastMonths){
        return new ResponseEntity<>(dashboardRepository.findByMonth(pastMonths), HttpStatus.OK);
    }


    @GetMapping("/orderMonths/projectDescriptions")
    public ResponseEntity<Iterable<String>> getAllProjectDescriptions(){
        return new ResponseEntity<>(dashboardRepository.findAllDescriptions(), HttpStatus.OK);
    }

    @GetMapping("/orderMonths/findByDescriptions/{description}")
    public ResponseEntity<Iterable<Project>> getProjectByDescriptions(String description){
        return new ResponseEntity<>(dashboardRepository.findAllByDescription(description), HttpStatus.OK);
    }
}
