package app.service.FileUtils;

import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.models.Order.OrderType;
import app.models.User.User;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderTypeRepository;
import app.repositories.Order.OrderlineRepository;
import app.repositories.Project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CSVHelper {

    private static final String TYPE = "text/csv";
    private static final String[] REQ_ORDER_HEADERS =
        {"Order Key", "Order Id Ext", "Order Date", "Payer User Key", "Order Type Key", "Transaction Total", "Currency", "Orderline Keys"};
    private static final String[] OPTIONAL_HEADERS =
        {"Created At", "Payment Method", "Description", "Order Stripe Payment Id",
            "Transaction Fee", "Transaction VAT", "User Id Ext", "Project", "Order User"};

    private static int orderKeyIndex = 0, orderIdExtIndex = 0, orderDateIndex = 0, payerUserKeyIndex = 0,
                orderTypeKeyIndex = 0, transactionTotalIndex = 0, currencyIndex = 0, orderLinesIndex = 0,
                //Optional
                createdAtIndex = 0, paymentMethodIndex = 0, descriptionIndex = 0, orderStripePaymentIdIndex = 0,
                transactionFeeIndex = 0, transactionVATIndex = 0, userIdExtIndex = 0, projectIndex = 0, orderUserIndex = 0;

    private final OrderTypeRepository orderTypeRepository;
    private final JPAUserRepository userRepository;
    private final OrderlineRepository orderlineRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public CSVHelper(OrderTypeRepository orderTypeRepository, JPAUserRepository userRepository, OrderlineRepository orderlineRepository, ProjectRepository projectRepository) {
        this.orderTypeRepository = orderTypeRepository;
        this.userRepository = userRepository;
        this.orderlineRepository = orderlineRepository;
        this.projectRepository = projectRepository;
    }


    public List<Order> CSVToOrders(MultipartFile file) {
        if (!hasCSVFormat(file)) throw new RuntimeException("You must upload a CSV file!");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String[] headers = br.readLine().split(",");
            int validation = headersValidation(headers);

            switch (validation) {
                case 0 -> {
                    return this.prepareOrdersList(headers, br, 1);
                }
                case 1 -> {
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

            if (this.orderTypeRepository.existsById(Integer.valueOf(values[orderTypeKeyIndex]))) {
                order.setOrderType(this.orderTypeRepository.findById(Integer.valueOf(values[orderTypeKeyIndex])).get());
            } else {
                OrderType orderType = new OrderType();
                orderType.setId(Integer.valueOf(values[orderTypeKeyIndex]));
                order.setOrderType(orderType);
            }
            order.setTransactionTotal(Double.parseDouble(values[transactionTotalIndex]));
            order.setCurrency(values[currencyIndex]);

            order.setOrderLines(this.getOrderlines(values[orderLinesIndex]));
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
                    order.setProject(this.projectRepository.findById(Integer.valueOf(values[projectIndex])));
                }
                if (optionalValues.contains("Order User")) {
                    order.setOrderUser(this.userRepository.findById(Integer.valueOf(values[orderUserIndex])));
                }
            }
            orders.add(order);
        }

        return orders;
    }

    private List<OrderLine> getOrderlines(String values) {
        List<OrderLine> ols = new ArrayList<>();
        System.out.println(values);
        this.orderlineRepository.findAll().forEach(orderLine -> {
            Arrays.stream(values.split(" "))
                  .filter(s -> orderLine.getId() == Integer.parseInt(s))
                  .map(s -> orderLine)
                  .forEach(ols::add);
        });
        return ols;
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
                            case "Created At" -> orderKeyIndex = i;
                            case "Payment Method" -> orderIdExtIndex = i;
                            case "Description" -> orderDateIndex = i;
                            case "Order Stripe Payment Id" -> payerUserKeyIndex = i;
                            case "Transaction Fee" -> orderTypeKeyIndex = i;
                            case "Transaction VAT" -> transactionTotalIndex = i;
                            case "User Id Ext" -> currencyIndex = i;
                            case "Project" -> projectIndex = i;
                            case "Order User" -> orderUserIndex = i;
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the headers of the CSV file are valid
     * @param headers the headers of the CSV file
     * @return int if the headers are valid and in what way:
     * -2 file contains invalid headers
     * -1 file contains some required headers
     *  0 file contains required and some optional headers
     *  1 file contains all required headers
     **/
    private static int headersValidation(String[] headers) {
        int reqHeaders = 0, optHeaders = 0;
        for (String header : headers) {
            if (List.of(REQ_ORDER_HEADERS).contains(header)) {
                reqHeaders++;
            } else if (List.of(OPTIONAL_HEADERS).contains(header)) {
                optHeaders++;
            } else return -2;
        }
        if (reqHeaders == REQ_ORDER_HEADERS.length && optHeaders == OPTIONAL_HEADERS.length) return 1;
        else if (reqHeaders == REQ_ORDER_HEADERS.length) return 0;
        else return -2;
    }

        public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}
