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
import com.google.common.collect.Iterators;
import org.hibernate.exception.SQLGrammarException;
import app.service.FileUtils.ImportTypes.Orders.OrderImport;
import app.service.FileUtils.ImportTypes.Orders.OrderlineImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    private final OrderImport orderImport;
    private final OrderlineImport orderlineImport;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderlineRepository orderlineRepository,
                           OrderTypeRepository orderTypeRepository, JPAUserRepository userRepository,
                           OrderImport orderImport, OrderlineImport orderlineImport,
                           JpaOrderRepository JpaorderRepository) {
        this.orderRepository = orderRepository;
        this.orderlineRepository = orderlineRepository;
        this.orderTypeRepository = orderTypeRepository;
        this.userRepository = userRepository;
        this.JpaorderRepository = JpaorderRepository;
        this.orderImport = orderImport;
        this.orderlineImport = orderlineImport;
    }


    @GetMapping("/orders")
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/orderlines/{id}")
    public HttpEntity<?> getOrderline(@PathVariable(value = "id") Integer orderlineId) {
        Optional<OrderLine> o = Optional.ofNullable(orderlineRepository.findById(orderlineId));
        return o.isPresent() ? new ResponseEntity<>(o, HttpStatus.OK) :
            new ResponseEntity<>(
                new ModelNotFound("Orderline", "id", orderlineId), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/orderlines")
    public ResponseEntity<Iterable<OrderLine>> getAllOrderlines() {
        return new ResponseEntity<>(orderlineRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            System.out.printf("order.typeKey: %s\n",order.typeKey);
            order.setCreatedAt(LocalDate.now());

            if (this.orderTypeRepository.findById(order.typeKey).isPresent()) {
                OrderType orderType = this.orderTypeRepository.findById(order.typeKey).get();
                order.setOrderType(orderType);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User u = this.userRepository.findById(order.payerKey);
            order.setPayer(u);
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
        Iterable<OrderLine> lines;
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
          Optional<Order> o = JpaorderRepository.findById(orderId);
            return o.isPresent() ?
                    new ResponseEntity<>(o, HttpStatus.OK) :
                    new ResponseEntity<>(
                            new ModelNotFound("Order", "id", orderId.toString()),
                            HttpStatus.NOT_FOUND);
        }catch (SQLGrammarException sqlGrammarException){
            return new ResponseEntity<>(sqlGrammarException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ModelNotFound("Order", "id", orderId), HttpStatus.NOT_FOUND);
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

    @PostMapping("/orders/importCSV")
    public ResponseEntity<?> importCSV(@RequestParam("files") MultipartFile[] files) {
        if (files.length <= 1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        MultipartFile orderlineFile = null, orderFile = null;
        try {
            int importingXOrders = 0, importingXOrderlines = 0, amountOfCurrentOrders = Iterators.size(orderRepository.findAll().iterator());
            for (MultipartFile file : files) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String line = bufferedReader.readLine();
                System.out.println(line);
                if (line != null) {
                    if (line.toLowerCase().contains("Owner User Key".toLowerCase())) {
                        orderlineFile = file;
                        while(bufferedReader.readLine() != null){
                            importingXOrderlines++;
                        }
                        importingXOrderlines--;
                        System.out.println("OrderlineFile found, with " + importingXOrderlines + " orderlines");
                    } else if (line.toLowerCase().contains("Orderline Keys".toLowerCase())) {
                        orderFile = file;
                        while(bufferedReader.readLine() != null){
                            importingXOrders++;
                        }
                        importingXOrders--;
                        System.out.println("OrderFile found, with " + importingXOrders + " orders");
                    }
                }
                bufferedReader.close();
            }
            List<OrderLine> orderlines = orderlineImport.CSVToOrderlines(orderlineFile, amountOfCurrentOrders, importingXOrders);
            orderlineRepository.saveAll(orderlines);
            List<Order> orders = orderImport.CSVToOrders(orderFile, orderlines);
            orderRepository.saveAll(orders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error reading files");
        }
        if (orderlineFile == null || orderFile == null) return ResponseEntity.internalServerError().body("OrderlineFile or OrderFile not found");



        return new ResponseEntity<>(HttpStatus.OK);
    }
}
