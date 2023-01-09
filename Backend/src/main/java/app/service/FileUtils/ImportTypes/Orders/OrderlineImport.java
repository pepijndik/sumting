package app.service.FileUtils.ImportTypes.Orders;

import app.models.Order.OrderLine;
import app.repositories.Batch.BatchRepository;
import app.repositories.JPAUserRepository;
import app.repositories.Order.OrderRepository;
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
import java.util.stream.Stream;

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
    private static int orderlineKeyIndex = 0, orderKeyIndex = 0, transactionLineTotalIndex = 0, productKeyIndex = 0,
        ownerUserKeyIndex = 0,
    //Optional
    notesIndex = 0, proofNameIndex = 0, proofDateIndex = 0, latitudeIndex = 0, longitudeIndex = 0, proofSmallIndex = 0,
        proofMediumIndex = 0, proofLargeIndex = 0, batchKeyIndex = 0, proofUploadedDatetimeIndex = 0,
        transactionLineFeeIndex = 0, transactionLineVATIndex = 0, orderlineStripeIdIndex = 0;

    private final OrderlineRepository orderlineRepository;
    private final JPAUserRepository userRepository;
    private final ProductRepository productRepository;
    private final BatchRepository batchRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderlineImport(OrderlineRepository orderlineRepository, JPAUserRepository userRepository,
                           ProductRepository productRepository, BatchRepository batchRepository,
                           OrderRepository orderRepository) {
        this.orderlineRepository = orderlineRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
        this.orderRepository = orderRepository;
    }

    private List<OrderLine> prepareOrderlinesList(String[] headers, MultipartFile multipartFile, int typeOfRequest) throws IOException {
        List<OrderLine> orderlines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
        assignIndexes(headers, 0);
        Stream<String> lines = br.lines();

        lines.forEach(line -> {
            if (line.contains("Orderline Key")) return;

            String[] values = line.split(",");
            System.out.println(line);
            OrderLine orderline = new OrderLine();
            if (orderlineRepository.existsById(Integer.parseInt(values[orderlineKeyIndex]))) return;
            orderline.setId(Integer.valueOf(values[orderlineKeyIndex]));
            orderline.setOrder(orderRepository.findById(Integer.valueOf(values[orderKeyIndex])));
            orderline.setTransactionLineTotal(Double.valueOf(values[transactionLineTotalIndex]));
            if (this.productRepository.existsById(Integer.valueOf(values[productKeyIndex])))
                orderline.setProduct(this.productRepository.findById(Integer.valueOf(values[productKeyIndex])));
            if (this.userRepository.existsById(Integer.valueOf(values[ownerUserKeyIndex])))
                orderline.setOwner(this.userRepository.findById(Integer.valueOf(values[ownerUserKeyIndex])));

            if (typeOfRequest == 1) {
                assignIndexes(headers, 1);
                List<String> optionalValues = Arrays.asList(headers).subList(REQ_ORDER_HEADERS.length, headers.length);
                if (optionalValues.contains("Notes")) orderline.setNotes(values[notesIndex]);
                if (optionalValues.contains("Proof Name")) orderline.setProofName(values[proofNameIndex]);
                if (optionalValues.contains("Proof Date")) orderline.setProofDate(LocalDateTime.parse(values[proofDateIndex]));
                if (optionalValues.contains("Latitude")) orderline.setLatitude(Double.valueOf(values[latitudeIndex]));
                if (optionalValues.contains("Longitude")) orderline.setLongitude(Double.valueOf(values[longitudeIndex]));
                if (optionalValues.contains("Proof Small")) orderline.setProofSmall(values[proofSmallIndex]);
                if (optionalValues.contains("Proof Medium")) orderline.setProofMedium(values[proofMediumIndex]);
                if (optionalValues.contains("Proof Large")) orderline.setProofLarge(values[proofLargeIndex]);
                if (this.batchRepository.existsById(Integer.valueOf(values[batchKeyIndex]))) orderline.setBatch(this.batchRepository.findById(Integer.valueOf(values[batchKeyIndex])));
                if (optionalValues.contains("Proof Uploaded Datetime")) orderline.setProofDate(LocalDateTime.parse(values[proofUploadedDatetimeIndex]));
                if (optionalValues.contains("Transaction Line Fee")) orderline.setTransactionLineFee(Double.valueOf(values[transactionLineFeeIndex]));
                if (optionalValues.contains("Transaction Line VAT")) orderline.setTransactionLineVat(Double.valueOf(values[transactionLineVATIndex]));
                if (optionalValues.contains("Orderline Stripe Id")) orderline.setStripeChargeId(values[orderlineStripeIdIndex]);
            }
            orderlines.add(orderline);
        });
        br.close();

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
            br.close();
            int validation = headersValidation(headers, REQ_ORDER_HEADERS, OPTIONAL_HEADERS);

            switch (validation) {
                case 0 -> {
                    return this.prepareOrderlinesList(headers, file, 1);
                }
                case 1 -> {
                    return this.prepareOrderlinesList(headers, file, 0);
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
