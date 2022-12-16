package app.rest;

import app.exceptions.ModelNotFound;
import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.models.Order.OrderType;
import app.models.User.User;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderRepository;
import app.repositories.Order.OrderTypeRepository;
import app.repositories.Order.OrderlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Nullable;
import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderlineRepository orderlineRepository;

    private final OrderTypeRepository orderTypeRepository;
    private final JPAUserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderlineRepository orderlineRepository, OrderTypeRepository orderTypeRepository, JPAUserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderlineRepository = orderlineRepository;
        this.orderTypeRepository = orderTypeRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/orders")
    public ResponseEntity<Iterable<Order>> getAllProjects() {
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public HttpEntity<?> getProject(@PathVariable(value = "id") Integer orderId) {
        Order o = orderRepository.findById(orderId);
        return orderRepository.findById(orderId) != null ? new ResponseEntity<>(o, HttpStatus.OK) : new ResponseEntity<ModelNotFound>(new ModelNotFound("Project", "id", orderId), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable(value = "id") Integer orderId) {
        Order OrderToDelete = orderRepository.findById(orderId);
        orderRepository.delete(OrderToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/orderlines")
    public ResponseEntity<Iterable<OrderLine>> getAllOrderlines() {
        return new ResponseEntity<>(orderlineRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/orderlines/{id}")
    public HttpEntity<?> getOrderline(@PathVariable(value = "id") Integer orderlineId) {
        OrderLine o = orderlineRepository.findById(orderlineId);
        return orderlineRepository.findById(orderlineId) != null ? new ResponseEntity<>(o, HttpStatus.OK) : new ResponseEntity<ModelNotFound>(new ModelNotFound("Orderline", "id", orderlineId), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            System.out.printf("order.typeKey: %s\n",order.typeKey);
            order.setCreatedAt(LocalDate.now());

            OrderType type = (OrderType)this.orderTypeRepository.findById(order.typeKey).get();
            User u = (User)this.userRepository.findById(order.payerKey);
            order.setPayer(u);
            order.setOrderType(type);
            System.out.printf("order.type: %s\n",order.getOrderType());
            Order newOrder = this.orderRepository.save(order);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOrder.getId()).toUri();
            return ResponseEntity.created(location).body(newOrder);

        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return ResponseEntity.internalServerError().body(order); //The provided data
        }
    }

    @PutMapping("/orders/editOrderlines/{id}")
    public ResponseEntity<OrderLine> editOrder(@PathVariable Integer id, @RequestBody OrderLine orderline) {
        try {
            Optional<OrderLine> findOrderline = Optional.of(orderlineRepository.findById(id));

            if (findOrderline.isPresent()) {
                OrderLine orderlineFound = findOrderline.get();
                orderlineFound.setNotes(orderline.getNotes());
                orderlineFound.setTransactionLineTotal(orderline.getTransactionLineTotal());
                orderlineFound.setProofName(orderline.getProofName());
                orderlineFound.setProofDate(orderline.getProofDate());
                orderlineFound.setLatitude(orderline.getLatitude());
                orderlineFound.setLongitude(orderline.getLongitude());
                orderlineFound.setProofSmall(orderline.getProofSmall());
                orderlineFound.setProofMedium(orderline.getProofMedium());
                orderlineFound.setProofLarge(orderline.getProofLarge());
                orderlineFound.setTransactionLineFee(orderline.getTransactionLineFee());
                orderlineFound.setTransactionLineVat(orderline.getTransactionLineVat());
                orderlineFound.setLoadedDate(orderline.getLoadedDate());

                return new ResponseEntity<>(orderlineRepository.save(orderlineFound), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/combinedSearch")
    public ResponseEntity<Iterable<Order>> getOrdersByClientAndProject(
        @RequestParam(value = "clientID", required = false) String clientId,
        @RequestParam(value = "projectID", required = false) String projectId) {
        if (!clientId.equals("null") && !projectId.equals("null")) {
            return new ResponseEntity<>(orderRepository.findByClientAndProject(Integer.parseInt(clientId), Integer.parseInt(projectId)), HttpStatus.OK);
        } else if (!clientId.equals("null")) {
            return new ResponseEntity<>(orderRepository.findByClient(Integer.parseInt(clientId)), HttpStatus.OK);
        } else if (!projectId.equals("null")) {
            return new ResponseEntity<>(orderRepository.findByProject(Integer.parseInt(projectId)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
