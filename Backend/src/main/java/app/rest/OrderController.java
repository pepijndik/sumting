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
    private final OrderlineRepository orderlineRepository;

    private final OrderTypeRepository orderTypeRepository;
    private final JPAUserRepository userRepository;
    private final OrderImport orderImport;
    private final OrderlineImport orderlineImport;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderlineRepository orderlineRepository,
                           OrderTypeRepository orderTypeRepository, JPAUserRepository userRepository,
                           OrderImport orderImport, OrderlineImport orderlineImport) {
        this.orderRepository = orderRepository;
        this.orderlineRepository = orderlineRepository;
        this.orderTypeRepository = orderTypeRepository;
        this.userRepository = userRepository;
        this.orderImport = orderImport;
        this.orderlineImport = orderlineImport;
    }


    @GetMapping("/orders")
    public ResponseEntity<Iterable<Order>> getAllProjects() {
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public HttpEntity<?> getProject(@PathVariable(value = "id") Integer orderId) {
        Order o = orderRepository.findById(orderId);
        return orderRepository.findById(orderId) != null ? new ResponseEntity<>(o, HttpStatus.OK) : new ResponseEntity<>(new ModelNotFound("Project", "id", orderId), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable(value = "id") Integer orderId) {
        Order OrderToDelete = orderRepository.findById(orderId);
        orderRepository.delete(OrderToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/orderlines/{id}")
    public HttpEntity<?> getOrderline(@PathVariable(value = "id") Integer orderlineId) {
        Optional<OrderLine> o = orderlineRepository.findById(orderlineId);
        return orderlineRepository.findById(orderlineId).isPresent() ? new ResponseEntity<>(o, HttpStatus.OK) : new ResponseEntity<>(new ModelNotFound("Orderline", "id", orderlineId), HttpStatus.NOT_FOUND);
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
            Optional<OrderLine> o = orderlineRepository.findById(Integer.valueOf(orderlineId.get()));
            return o.isPresent() ?
                    new ResponseEntity<>(o, HttpStatus.OK) :
                    new ResponseEntity<>(
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

    @PutMapping("/orderlines/editOrderlines/{id}")
    public ResponseEntity<OrderLine> editOrder(@PathVariable Integer id, @RequestBody OrderLine orderline){
        try {
            Optional<OrderLine> findOrderline = orderlineRepository.findById(id);

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
            for (MultipartFile file : files) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String line = bufferedReader.readLine();
                System.out.println(line);
                if (line != null) {
                    if (line.toLowerCase().contains("Owner User Key".toLowerCase())) {
                        orderlineFile = file;
                        System.out.println("OrderlineFile found");
                    } else if (line.toLowerCase().contains("Orderline Keys".toLowerCase())) {
                        orderFile = file;
                        System.out.println("OrderFile found");
                    }
                }
                bufferedReader.close();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error reading files");
        }
        if (orderlineFile == null || orderFile == null) return ResponseEntity.internalServerError().body("OrderlineFile or OrderFile not found");

        List<OrderLine> orderlines = orderlineImport.CSVToOrderlines(orderlineFile);
        orderlineRepository.saveAll(orderlines);
        List<Order> orders = orderImport.CSVToOrders(orderFile, orderlines);
        orderRepository.saveAll(orders);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
