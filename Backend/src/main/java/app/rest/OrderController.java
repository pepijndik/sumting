package app.rest;

import app.exceptions.ModelNotFound;
import app.models.Order.Order;
import app.repositories.Order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public HttpEntity<?> getProject(@PathVariable(value = "id") Integer orderId) {
        Order o = orderRepository.findById(orderId);
        return orderRepository.findById(orderId) != null ? new ResponseEntity<>(o, HttpStatus.OK) : new ResponseEntity<ModelNotFound>(new ModelNotFound("Project", "id", orderId),HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable(value = "id") Integer orderId) {
        Order OrderToDelete = orderRepository.findById(orderId);
        orderRepository.delete(OrderToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/orders/editOrder/{id}")
    public ResponseEntity<Order> editOrder(@PathVariable Integer id, @RequestBody Order order){
        try {
            Optional<Order> findOrder = Optional.ofNullable(orderRepository.findById(id));

            if (findOrder.isPresent()){
                Order orderFound = findOrder.get();
                orderFound.setCurrency(order.getCurrency());
                orderFound.setDescription(order.getDescription());
                orderFound.setPaymentMethod(order.getPaymentMethod());
                orderFound.setTransactionFee(order.getTransactionFee());
                orderFound.setTransactionTotal(order.getTransactionTotal());
                orderFound.setTransactionVat(order.getTransactionVat());
                orderFound.setOrder_date(order.getOrder_date());

                return new ResponseEntity<>(orderRepository.save(orderFound), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
