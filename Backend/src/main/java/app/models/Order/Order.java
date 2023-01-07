package app.models.Order;

import app.models.Project.Project;
import app.views.OrderView;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import jdk.jfr.Timestamp;
import app.models.Identifiable;
import app.models.User.User;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order implements Identifiable<Integer> {
    public static final String TABLE_NAME = "\"order\"";


    public Order() {
    }
    public Order(Integer id,
                 LocalDate order_date,
                 String description,
                 Double transactionTotal,
                 String currency,
                 User payer,
                 OrderType type,
                 Project project) {
        this.id = id;
        this.order_date = order_date;
        this.description = description;
        this.transactionTotal = transactionTotal;
        this.currency = currency;
        this.payer = payer;
        this.orderType = type;
        this.project = project;
    }

    @Id()
    @JsonView(OrderView.Order.class)
    @Column(name = "order_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonView(OrderView.Order.class)
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

    @Nullable
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JsonView(OrderView.Order.class)
    @JoinColumn(name = "order_type_key", referencedColumnName = "order_type_key", updatable = true, insertable = true)
    private OrderType orderType;

    @Transient
    public Integer typeKey;
    @Transient
    public Integer payerKey;

    @Nullable
    @JsonView(OrderView.Order.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project", referencedColumnName = "project_key",   nullable = true,
            updatable = false, insertable = false)
    private Project project;

    @Nullable
    @JsonView(OrderView.Order.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_user", referencedColumnName = "user_key",
            nullable = true,
            updatable = false, insertable = false)
    private User orderUser;

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
    private String currency;

    @Nullable
    @JsonView(OrderView.Order.class)
    @JoinColumn(name = "user_id_ext", columnDefinition = "int")
    @OneToOne(cascade = CascadeType.DETACH, optional = true)
    private User user;


    @Nullable
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_key", referencedColumnName = "order_key", updatable = true, insertable = true,nullable = true)
    private List<OrderLine> orderLines;



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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public User getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(User orderUser) {
        this.orderUser = orderUser;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
