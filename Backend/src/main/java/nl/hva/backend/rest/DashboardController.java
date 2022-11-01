package nl.hva.backend.rest;

import nl.hva.backend.models.Dashboard.Graph;
import nl.hva.backend.repositories.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


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

}
