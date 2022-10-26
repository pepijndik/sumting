package nl.hva.backend.models.Order;

import com.sun.istack.Nullable;
import jdk.jfr.Timestamp;
import nl.hva.backend.models.Identifiable;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order implements Identifiable<Integer> {
    public static final String TABLE_NAME = "order";

    @Id()
    @Column(name = "order_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Order() {

    }

    public Order(Integer id, Integer orderIdExt, LocalDate createdAt, Character paymentMethod, LocalDate order_date, String description, Double transactionTotal, OrderType orderTypeKey, Double transactionFee, Double transactionVat, Character currency) {
        this.id = id;
        this.orderIdExt = orderIdExt;
        this.createdAt = createdAt;
        this.paymentMethod = paymentMethod;

        this.order_date = order_date;
        this.description = description;
        this.transactionTotal = transactionTotal;
        this.orderTypeKey = orderTypeKey;
        this.transactionFee = transactionFee;
        this.transactionVat = transactionVat;
        this.currency = currency;
    }

    @Nullable
    @Column(name = "order_id_ext", columnDefinition = "int")
    private Integer orderIdExt;

    @Timestamp
    @Column(name = "created_at", columnDefinition = "timestamp")
    private LocalDate createdAt;

    @Nullable
    @Column(name = "payment_method", columnDefinition = "char")
    private Character paymentMethod;


    @Timestamp
    @Column(name = "order_date", columnDefinition = "timestamp")
    private LocalDate order_date;

    @Nullable
    @Column(name = "description", columnDefinition = "varchar(255)")
    private String description;



    @Nullable
    @Column(name = "transaction_total", columnDefinition = "double")
    private Double transactionTotal;

    @OneToOne
    @JoinColumn(name = "order_type_key",updatable = false, insertable = false)
    private OrderType orderTypeKey;

    @Nullable
    @Column(name = "transaction_fee", columnDefinition = "double")
    private Double transactionFee;

    @Nullable
    @Column(name = "transaction_vat", columnDefinition = "double")
    private Double transactionVat;

    @Nullable
    @Column(name = "currency", columnDefinition = "char(5)")
    private Character currency;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        System.out.println("Set id: " + id);
        this.id = id;
    }

    public Integer getOrderIdExt() {
        return orderIdExt;
    }

    public void setOrderIdExt(Integer orderIdExt) {
        this.orderIdExt = orderIdExt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Character getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Character paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Double getTransactionTotal() {
        return transactionTotal;
    }

    public void setTransactionTotal(Double transactionTotal) {
        this.transactionTotal = transactionTotal;
    }

    public Double getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(Double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public Double getTransactionVat() {
        return transactionVat;
    }

    public void setTransactionVat(Double transactionVat) {
        this.transactionVat = transactionVat;
    }

    public Character getCurrency() {
        return currency;
    }

    public void setCurrency(Character currency) {
        this.currency = currency;
    }



    public OrderType getOrderTypeKey() {
        return orderTypeKey;
    }

    public void setOrderTypeKey(OrderType orderTypeKey) {
        this.orderTypeKey = orderTypeKey;
    }
}
