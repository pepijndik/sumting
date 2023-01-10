package app.rest;

import app.exceptions.ModelNotFound;
import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.models.Order.OrderType;
import app.models.User.User;
import app.repositories.Interfaces.JpaOrderRepository;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderRepository;
import app.repositories.Order.OrderTypeRepository;
import app.repositories.Order.OrderlineRepository;
import app.views.OrderLineView;
import app.views.OrderView;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;

    private final JpaOrderRepository JpaorderRepository;
    private final OrderlineRepository orderlineRepository;

    private final OrderTypeRepository orderTypeRepository;
    private final JPAUserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderlineRepository orderlineRepository, OrderTypeRepository orderTypeRepository, JPAUserRepository userRepository, JpaOrderRepository JpaorderRepository) {
        this.orderRepository = orderRepository;
        this.orderlineRepository = orderlineRepository;
        this.orderTypeRepository = orderTypeRepository;
        this.userRepository = userRepository;
        this.JpaorderRepository = JpaorderRepository;
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
    @JsonView(OrderView.Order.class)
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrder(@PathVariable(value = "id") Integer orderId) {
        try{
          Optional<Order> o =JpaorderRepository.findById(orderId);
            return o.isPresent() ?
                    new ResponseEntity<>(o, HttpStatus.OK) :
                    new ResponseEntity<ModelNotFound>(
                            new ModelNotFound("Order", "id", orderId.toString()),
                            HttpStatus.NOT_FOUND);
        }catch (SQLGrammarException sqlGrammarException){
            return new ResponseEntity<>(sqlGrammarException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<ModelNotFound>(new ModelNotFound("Order", "id", orderId), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/orderlines/editOrderlines/{id}")
    public ResponseEntity<OrderLine> editOrder(@PathVariable Integer id, @RequestBody OrderLine orderline){
        try {
            Optional<OrderLine> findOrderline = Optional.ofNullable(orderlineRepository.findById(id));

            if (!findOrderline.isPresent()){
                return ResponseEntity.notFound().build();
            }

            OrderLine orderlineFound = findOrderline.get();
            if (orderline.getNotes() != null){
                orderlineFound.setNotes(orderline.getNotes());
            }
            if (orderline.getTransactionLineTotal() != null){
                orderlineFound.setTransactionLineTotal(orderline.getTransactionLineTotal());
            }

            if (orderline.getProofName() != null){
                orderlineFound.setProofName(orderline.getProofName());
            }

            if (orderline.getLatitude() != null){
                orderlineFound.setLatitude(orderline.getLatitude());
            }

            if (orderline.getLongitude() != null){
                orderlineFound.setLongitude(orderline.getLongitude());
            }

            if (orderline.getProofSmall() != null){
                orderlineFound.setProofSmall(orderline.getProofSmall());
            }

            if (orderline.getProofMedium() != null){
                orderlineFound.setProofMedium(orderline.getProofMedium());
            }

            if (orderline.getProofLarge() != null){
                orderlineFound.setProofLarge(orderline.getProofLarge());
            }

            if (orderline.getTransactionLineFee() != null){
                orderlineFound.setTransactionLineFee(orderline.getTransactionLineFee());
            }

            if (orderline.getTransactionLineVat() != null){
                orderlineFound.setTransactionLineVat(orderline.getTransactionLineVat());
            }

            if (orderline.getLoadedDate() != null){
                orderlineFound.setLoadedDate(orderline.getLoadedDate());
            }

            if (orderline.getProofUploadDate() != null){
                orderlineFound.setProofUploadDate(orderline.getProofUploadDate());
            }
            OrderLine updatedOrderLine = orderlineRepository.save(orderlineFound);
            return ResponseEntity.ok(updatedOrderLine);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orderlines/findByNotes/{description}")
    public ResponseEntity<Iterable<OrderLine>> getOrdelinesByNotes(@PathVariable String description){
        return new ResponseEntity<>(orderlineRepository.getOrderLineByNotes(description), HttpStatus.OK);
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

    @DeleteMapping("/orders/orderlines/{id}")
    public ResponseEntity<Void> deleteOrderline(@PathVariable(value = "id") Integer orderId) {
        OrderLine OrderLineToDelete = orderlineRepository.findById(orderId);
//        Integer OrderKey = orderlineRepository.findById(orderId).getId();

//        if (OrderKey != null){
//            deleteOrder(OrderKey);
//        }

        orderlineRepository.delete(OrderLineToDelete);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
