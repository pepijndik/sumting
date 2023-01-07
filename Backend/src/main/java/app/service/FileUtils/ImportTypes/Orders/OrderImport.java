package app.service.FileUtils.ImportTypes.Orders;

import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.models.Order.OrderType;
import app.models.User.User;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderTypeRepository;
import app.repositories.Project.ProjectServiceImpl;
import app.service.FileUtils.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OrderImport extends CSVHelper {
    private static final String[] REQ_ORDER_HEADERS = {
        "Order Key", "Order Id Ext", "Order Date", "Payer User Key", "Order Type Key", "Transaction Total", "Currency",
        "Orderline Keys"
    };
    private static final String[] OPTIONAL_HEADERS = {
        "Created At", "Payment Method", "Description", "Transaction Fee", "Transaction VAT", "User Id Ext", "Project",
        "Order User"
    };

    private static int orderKeyIndex = 0, orderIdExtIndex = 0, orderDateIndex = 0, payerUserKeyIndex = 0,
        orderTypeKeyIndex = 0, transactionTotalIndex = 0, currencyIndex = 0, orderLinesIndex = 0,
        //Optional
        createdAtIndex = 0, paymentMethodIndex = 0, descriptionIndex = 0,
        transactionFeeIndex = 0, transactionVATIndex = 0, userIdExtIndex = 0, projectIndex = 0, orderUserIndex = 0;

    private List<OrderLine> orderLines;
    private final JPAUserRepository userRepository;
    private final OrderTypeRepository orderTypeRepository;
    private final ProjectServiceImpl projectService;

    @Autowired
    public OrderImport(JPAUserRepository userRepository, OrderTypeRepository orderTypeRepository,
                       ProjectServiceImpl projectService) {
        this.userRepository = userRepository;
        this.orderTypeRepository = orderTypeRepository;
        this.projectService = projectService;
    }

    private List<Order> prepareOrdersList(String[] headers, BufferedReader br, int typeOfRequest) throws IOException {
        List<Order> orders = new ArrayList<>();

        assignIndexes(headers, 0);

        while (br.readLine() != null) {
            String[] values = br.readLine().split(",");
            Order order = new Order();
            order.setId(Integer.parseInt(values[orderKeyIndex]));
            order.setOrderIdExt(Integer.valueOf(values[orderIdExtIndex]));
            order.setOrder_date(LocalDate.parse(values[orderDateIndex]));
            if (this.userRepository.existsById(Integer.parseInt(values[payerUserKeyIndex]))) {
                order.setPayer(this.userRepository.findById(Integer.parseInt(values[payerUserKeyIndex])));
            } else {
                User user = new User();
                user.setId(Integer.parseInt(values[payerUserKeyIndex]));
                userRepository.save(user);
            }

            order.setPayer(this.userRepository.findById(Integer.valueOf(values[payerUserKeyIndex])));

            if (this.orderTypeRepository.findById(Integer.valueOf(values[orderTypeKeyIndex])).isPresent()) {
                order.setOrderType(this.orderTypeRepository.findById(Integer.valueOf(values[orderTypeKeyIndex])).get());
            } else {
                OrderType orderType = new OrderType();
                orderType.setId(Integer.valueOf(values[orderTypeKeyIndex]));
                order.setOrderType(orderType);
            }
            order.setTransactionTotal(Double.parseDouble(values[transactionTotalIndex]));
            order.setCurrency(values[currencyIndex]);

            List<OrderLine> currentOls = this.orderLines.stream().map(orderLine -> {
                if (values[orderLinesIndex].contains(String.valueOf(orderLine.getId()))) {
                    return orderLine;
                }
                return null;
            }).toList();
            if (currentOls.size() > 0) {
                order.setOrderLines(currentOls);
            }
            //If 1 add optional fields
            if (typeOfRequest == 1) {
                assignIndexes(headers, 1);
                List<String> optionalValues = Arrays.asList(headers).subList(REQ_ORDER_HEADERS.length, headers.length);
                if (optionalValues.contains("Created At")) {
                    order.setCreatedAt(LocalDate.parse(values[createdAtIndex]));
                }
                if (optionalValues.contains("Payment Method")) {
                    order.setPaymentMethod(values[paymentMethodIndex].charAt(0));
                }
                if (optionalValues.contains("Description")) {
                    order.setDescription(values[descriptionIndex]);
                }
                if (optionalValues.contains("Transaction Fee")) {
                    order.setTransactionFee(Double.parseDouble(values[transactionFeeIndex]));
                }
                if (optionalValues.contains("Transaction VAT")) {
                    order.setTransactionVat(Double.parseDouble(values[transactionVATIndex]));
                }
                if (optionalValues.contains("User Id Ext")) {
                    order.setUser(this.userRepository.findById(Integer.parseInt(values[userIdExtIndex])));
                }
                if (optionalValues.contains("Project")) {
                    if (this.projectService.findById(Integer.parseInt(values[projectIndex])).isPresent()) {
                        order.setProject(this.projectService.findById(Integer.parseInt(values[projectIndex])).get());
                    }
                }
                if (optionalValues.contains("Order User")) {
                    order.setOrderUser(this.userRepository.findById(Integer.valueOf(values[orderUserIndex])));
                }
            }
            orders.add(order);
        }

        return orders;
    }

    private static void assignIndexes(String[] headers, int assignmentType) {
        for (int i = 0; i < headers.length; i++) {
            switch (headers[i]) {
                case "Order Key" -> orderKeyIndex = i;
                case "Order Id Ext" -> orderIdExtIndex = i;
                case "Order Date" -> orderDateIndex = i;
                case "Payer User Key" -> payerUserKeyIndex = i;
                case "Order Type Key" -> orderTypeKeyIndex = i;
                case "Transaction Total" -> transactionTotalIndex = i;
                case "Currency" -> currencyIndex = i;
                case "Order Lines" -> orderLinesIndex = i;
                default -> { // Optional fields
                    if (assignmentType == 1) {
                        switch (headers[i]) {
                            case "Created At" -> createdAtIndex = i;
                            case "Payment Method" -> paymentMethodIndex = i;
                            case "Description" -> descriptionIndex = i;
                            case "Transaction Fee" -> transactionFeeIndex = i;
                            case "Transaction VAT" -> transactionVATIndex = i;
                            case "User Id Ext" -> userIdExtIndex = i;
                            case "Project" -> projectIndex = i;
                            case "Order User" -> orderUserIndex = i;
                        }
                    }
                }
            }
        }
    }

    public List<Order> CSVToOrders(MultipartFile file, List<OrderLine> importedOrderlines) {
        if (!hasCSVFormat(file)) throw new RuntimeException("You must upload a CSV file!");
        if (importedOrderlines.isEmpty()) throw new RuntimeException("You must upload a CSV file with orderlines!");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String[] headers = br.readLine().split(",");

            switch (headersValidation(headers, REQ_ORDER_HEADERS, OPTIONAL_HEADERS)) {
                case 0 -> {
                    this.orderLines = importedOrderlines;
                    return this.prepareOrdersList(headers, br, 1);
                }
                case 1 -> {
                    this.orderLines = importedOrderlines;
                    return this.prepareOrdersList(headers, br, 0);
                }
                default -> {
                    System.out.println("Invalid CSV file");
                    return null;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
