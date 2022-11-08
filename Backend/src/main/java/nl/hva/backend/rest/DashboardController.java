package nl.hva.backend.rest;

import nl.hva.backend.models.Dashboard.Graph;
import nl.hva.backend.repositories.DashboardRepository;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;


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

}
