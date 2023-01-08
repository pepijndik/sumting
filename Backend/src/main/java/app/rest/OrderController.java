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
import app.views.OrderLineView;
import app.views.OrderView;
import com.fasterxml.jackson.annotation.JsonView;
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
import java.util.ArrayList;
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
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
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


    @GetMapping("/orders/types")
    public ResponseEntity<Iterable<OrderType>> getTypes(){
        return new ResponseEntity<>(orderTypeRepository.findAll(), HttpStatus.OK);
    }
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(value = "id") Integer orderId) {
        Order OrderToDelete = orderRepository.findById(orderId);
        orderRepository.delete(OrderToDelete);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @JsonView({OrderLineView.OrderLine.class})
    @GetMapping(value = {
            "/orders/orderlines",
            "/orders/orderlines/{id}" })
    public ResponseEntity<?> getAllOrderlinesBy(
            @PathVariable(value = "id",required = false) Optional<String> orderlineId,
            @RequestParam(name="productId",required=false) Integer product_id,
            @RequestParam(name="orderId",required=false) Integer order_id
    ) {
        Iterable<OrderLine> lines = null;
        if(orderlineId.isPresent()){
            Optional<OrderLine> o = Optional.ofNullable(orderlineRepository.findById(Integer.valueOf(orderlineId.get())));
            return o.isPresent() ?
                    new ResponseEntity<>(o, HttpStatus.OK) :
                    new ResponseEntity<ModelNotFound>(
                            new ModelNotFound("Orderline", "id", orderlineId.get()),
                            HttpStatus.NOT_FOUND);
        }
        if(product_id != null || order_id != null){
            lines = orderlineRepository.findAllBy(product_id, order_id);

        }else{
            lines = orderlineRepository.findAll();
        }
        return new ResponseEntity<>(lines, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrder(@PathVariable(value = "id") Integer orderId) {
        try{
            Optional<Order> o = Optional.ofNullable(orderRepository.findById(orderId));
            return o.isPresent() ?
                    new ResponseEntity<>(o, HttpStatus.OK) :
                    new ResponseEntity<ModelNotFound>(
                            new ModelNotFound("Order", "id", orderId.toString()),
                            HttpStatus.NOT_FOUND);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<ModelNotFound>(new ModelNotFound("Order", "id", orderId), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/orderlines/editOrderlines/{id}")
    public ResponseEntity<OrderLine> editOrder(@PathVariable Integer id, @RequestBody OrderLine orderline){
        try {
            Optional<OrderLine> findOrderline = Optional.ofNullable(orderlineRepository.findById(id));

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
                orderlineFound.setProofUploadDate(orderline.getProofUploadDate());

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
