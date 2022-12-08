package app.rest;

import app.models.Order.OrderLine;
import app.repositories.Order.OrderlineRepostory;
import app.views.OrderLineView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderlineController {
    private final OrderlineRepostory orderlineRepostory;

    @Autowired
    public OrderlineController(OrderlineRepostory orderlineRepostory) {
        this.orderlineRepostory = orderlineRepostory;
    }

    @GetMapping("/orderlines")
    public ResponseEntity<Iterable<OrderLine>> getAllOrderlines() {
        return new ResponseEntity<>(orderlineRepostory.findAll(), HttpStatus.OK);
    }

    @GetMapping("/orderlines/{id}")
    @JsonView(OrderLineView.OrderLine.class)
    public HttpEntity<?> getOrderline(@PathVariable(value = "id") Integer orderlineId) {
        OrderLine orderLine = orderlineRepostory.findById(orderlineId);
        return orderLine != null ? new ResponseEntity<>(orderLine, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/orderlines/{id}")
    public ResponseEntity<Void> deleteOrderline(@PathVariable(value = "id") Integer orderlineid) {
        OrderLine OrderlineToDelete = orderlineRepostory.findById(orderlineid);
        orderlineRepostory.delete(OrderlineToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/orderlines/combinedSearch")
    public ResponseEntity<Iterable<OrderLine>> getOrderLinesByClientAndProject(
            @RequestParam(value = "clientID") Integer clientID,
            @RequestParam(value = "projectID") Integer projectId) {
        if (clientID != null && projectId != null) {
            return new ResponseEntity<>(orderlineRepostory.findByClientAndProject(clientID, projectId), HttpStatus.OK);
        } else if (clientID != null) {
            return new ResponseEntity<>(orderlineRepostory.findByClient(clientID), HttpStatus.OK);
        } else if (projectId != null) {
            return new ResponseEntity<>(orderlineRepostory.findByProject(projectId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
