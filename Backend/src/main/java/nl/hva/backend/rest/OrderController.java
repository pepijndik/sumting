package nl.hva.backend.rest;

import nl.hva.backend.exceptions.ModelNotFound;
import nl.hva.backend.models.Order.Order;
import nl.hva.backend.models.Project.Project;
import nl.hva.backend.repositories.OrderRepository;
import nl.hva.backend.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @GetMapping("/orders")
    public ResponseEntity<Iterable<Order>> getAllProjects() {
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public HttpEntity<?> getProject(@PathVariable(value = "id") Integer projectId) {
        Order p = orderRepository.findById(projectId);
        return orderRepository.findById(projectId) != null ? new ResponseEntity<>(p, HttpStatus.OK) : new ResponseEntity<ModelNotFound>(new ModelNotFound("Project", "id", projectId),HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId) {
        Order OrderToDelete = orderRepository.findById(projectId);
        orderRepository.delete(OrderToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
