package app.service.FileUtils.ImportTypes.Orders;

import app.models.Order.OrderLine;
import app.repositories.Batch.BatchRepository;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderlineRepository;
import app.repositories.ProductRepository;
import app.service.FileUtils.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OrderlineImport extends CSVHelper {
    private static final String[] REQ_ORDER_HEADERS = {
        "Orderline Key", "Order Key", "Transaction Line Total", "Product Key", "Owner User Key",
    };
    private static final String[] OPTIONAL_HEADERS = {
        "Order Id Ext", "Orderline Id Ext", "Notes", "Product Id Ext", "Wallet Key", "Proof Name", "Proof Date",
        "Latitude", "Longitude", "Proof Small", "Proof Medium", "Proof Large", "Batch Key", "Proof Uploaded Datetime",
        "Transaction Line Fee", "Transaction Line VAT", "Orderline Stripe Id", "Proof Id", "Project Key", "Price"
    };

    private final OrderlineRepository orderlineRepository;
    private final JPAUserRepository userRepository;
    private final ProductRepository productRepository;
    private final BatchRepository batchRepository;

    private static int orderlineKeyIndex = 0, orderKeyIndex = 0, transactionLineTotalIndex = 0, productKeyIndex = 0,
    ownerUserKeyIndex = 0,
    //Optional
    notesIndex = 0, proofNameIndex = 0, proofDateIndex = 0, latitudeIndex = 0, longitudeIndex = 0, proofSmallIndex = 0,
    proofMediumIndex = 0, proofLargeIndex = 0, batchKeyIndex = 0, proofUploadedDatetimeIndex = 0,
    transactionLineFeeIndex = 0, transactionLineVATIndex = 0, orderlineStripeIdIndex = 0;


    @Autowired
    public OrderlineImport(OrderlineRepository orderlineRepository, JPAUserRepository userRepository,
                           ProductRepository productRepository, BatchRepository batchRepository) {
        this.orderlineRepository = orderlineRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
    }

    private List<OrderLine> prepareOrderlinesList(String[] headers, BufferedReader br, int typeOfRequest) throws IOException {
        List<OrderLine> orderlines = new ArrayList<>();

        assignIndexes(headers, 0);

        while (br.readLine() != null) {
            String[] values = br.readLine().split(",");
            OrderLine orderline = new OrderLine();
            orderline.setId(Integer.valueOf(values[orderlineKeyIndex]));
            orderline.setOrderKey(Integer.valueOf(values[orderKeyIndex]));
            orderline.setTransactionLineTotal(Double.valueOf(values[transactionLineTotalIndex]));
            orderline.setProduct(this.productRepository.findById(Integer.valueOf(values[productKeyIndex])));
            orderline.setOwner(this.userRepository.findById(Integer.valueOf(values[ownerUserKeyIndex])));
            if (typeOfRequest == 1) {
                assignIndexes(headers, 1);
                orderline.setNotes(values[notesIndex]);
                orderline.setProofName(values[proofNameIndex]);
                orderline.setProofDate(LocalDateTime.parse(values[proofDateIndex]));
                orderline.setLatitude(Double.valueOf(values[latitudeIndex]));
                orderline.setLongitude(Double.valueOf(values[longitudeIndex]));
                orderline.setProofSmall(values[proofSmallIndex]);
                orderline.setProofMedium(values[proofMediumIndex]);
                orderline.setProofLarge(values[proofLargeIndex]);
                orderline.setBatch(batchRepository.findById(Integer.valueOf(values[batchKeyIndex])));
                orderline.setProofUploadDate(LocalDateTime.parse(values[proofUploadedDatetimeIndex]));
                orderline.setTransactionLineFee(Double.valueOf(values[transactionLineFeeIndex]));
                orderline.setTransactionLineVat(Double.valueOf(values[transactionLineVATIndex]));
                orderline.setStripeChargeId(values[orderlineStripeIdIndex]);
            }
            orderlines.add(orderline);
        }

        return orderlines;
    }

    private static void assignIndexes(String[] headers, int assignmentType) {
        for (int i = 0; i < headers.length; i++) {
            switch (headers[i]) {
                case "Orderline Key" -> orderlineKeyIndex = i;
                case "Order Key" -> orderKeyIndex = i;
                case "Transaction Line Total" -> transactionLineTotalIndex = i;
                case "Product Key" -> productKeyIndex = i;
                case "Owner User Key" -> ownerUserKeyIndex = i;
                default -> {
                    if (assignmentType == 1) {
                        switch (headers[i]) {
                            case "Notes" -> notesIndex = i;
                            case "Proof Name" -> proofNameIndex = i;
                            case "Proof Date" -> proofDateIndex = i;
                            case "Latitude" -> latitudeIndex = i;
                            case "Longitude" -> longitudeIndex = i;
                            case "Proof Small" -> proofSmallIndex = i;
                            case "Proof Medium" -> proofMediumIndex = i;
                            case "Proof Large" -> proofLargeIndex = i;
                            case "Batch Key" -> batchKeyIndex = i;
                            case "Proof Uploaded Datetime" -> proofUploadedDatetimeIndex = i;
                            case "Transaction Line Fee" -> transactionLineFeeIndex = i;
                            case "Transaction Line VAT" -> transactionLineVATIndex = i;
                            case "Orderline Stripe Id" -> orderlineStripeIdIndex = i;
                        }
                    }
                }
            }
        }
    }

    public List<OrderLine> CSVToOrderlines(MultipartFile file) {
        if (!hasCSVFormat(file)) throw new RuntimeException("You must upload a CSV file!");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String[] headers = br.readLine().split(",");
            int validation = headersValidation(headers, REQ_ORDER_HEADERS, OPTIONAL_HEADERS);

            switch (validation) {
                case 0 -> {
                    return this.prepareOrderlinesList(headers, br, 1);
                }
                case 1 -> {
                    return this.prepareOrderlinesList(headers, br, 0);
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

    private List<OrderLine> getOrderlines(String values) {
        List<OrderLine> ols = new ArrayList<>();
        System.out.println(values);
        this.orderlineRepository.findAll().forEach(orderLine -> Arrays.stream(values.split(" "))
            .filter(s -> orderLine.getId() == Integer.parseInt(s))
            .map(s -> orderLine)
            .forEach(ols::add));
        return ols;
    }
}
