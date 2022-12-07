package app.models.Order;

import app.views.OrderView;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import jdk.jfr.Timestamp;
import app.models.Identifiable;
import app.models.User.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order implements Identifiable<Integer> {
    public static final String TABLE_NAME = "\"order\"";

    @Id()
    @JsonView(OrderView.Order.class)
    @Column(name = "order_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Nullable
    @Column(name = "order_id_ext", columnDefinition = "int")
    private Integer orderIdExt;

    @Timestamp
    @JsonView(OrderView.Order.class)
    @Column(name = "created_at", columnDefinition = "timestamp")
    private LocalDate createdAt;

    @Nullable
    @JsonView(OrderView.Order.class)
    @Column(name = "payment_method", columnDefinition = "char")
    private Character paymentMethod;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "payer_user_key", columnDefinition = "int")
    private User payer;

    @Timestamp
    @JsonView(OrderView.Order.class)
    @Column(name = "order_date", columnDefinition = "timestamp")
    private LocalDate order_date;

    @Nullable
    @JsonView(OrderView.Order.class)
    @Column(name = "description", columnDefinition = "varchar(255)")
    private String description;



    @Nullable
    @JsonView(OrderView.Order.class)
    @Column(name = "transaction_total", columnDefinition = "double")
    private Double transactionTotal;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JsonView(OrderView.Order.class)
    @JoinColumn(name = "order_type_key",referencedColumnName = "order_type_key",updatable = false, insertable = false)
    private OrderType orderTypeKey;

    @Nullable
    @JsonView(OrderView.Order.class)
    @Column(name = "transaction_fee", columnDefinition = "double")
    private Double transactionFee;

    @Nullable
    @JsonView(OrderView.Order.class)
    @Column(name = "transaction_vat", columnDefinition = "double")
    private Double transactionVat;

    @Nullable
    @JsonView(OrderView.Order.class)
    @Column(name = "currency", columnDefinition = "char(5)")
    private Character currency;

    @Nullable
    @JsonView(OrderView.Order.class)
    @JoinColumn(name = "user_id_ext", columnDefinition = "int")
    @OneToOne(cascade = CascadeType.DETACH, optional = true)
    private User user;


//    private List<OrderLine> orderLines;

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
