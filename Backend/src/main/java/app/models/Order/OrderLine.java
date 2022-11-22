package app.models.Order;


import app.models.Batch.Batch;
import app.models.Identifiable;
import app.models.Product.Product;
import app.models.User.User;
import app.models.Wallet;
import app.views.OrderLineView;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = OrderLine.TABLE_NAME)
public class OrderLine implements Identifiable<Integer> {
    public static final String TABLE_NAME = "orderline_contribution";

    @Id
    @Column(name = "orderline_key", nullable = false)
    @JsonView(OrderLineView.OrderLine.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonView(OrderLineView.OrderLine.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_key", referencedColumnName = "order_key", insertable = false, updatable = false)
    private Order order;

    @Column(name = "order_key", nullable = true, columnDefinition = "int default 0")
    private Integer orderKey;

    @JsonView(OrderLineView.OrderLine.class)
    @Column(name = "quantity", nullable = true, columnDefinition = "int default 0")
    private Integer quantity;

    @JsonView(OrderLineView.OrderLine.class)
    @Column(name = "notes", nullable = true)
    private String notes;

    @JsonView(OrderLineView.OrderLine.class)
    @Column(name = "transaction_line_total", nullable = true, columnDefinition = "double default 0.0")
    private Double transactionLineTotal;

    @JsonView(OrderLineView.OrderLine.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_key", referencedColumnName = "product_key", insertable = false, updatable = false)
    private Product product;

    @JsonView(OrderLineView.OrderLine.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_key", referencedColumnName = "user_key", insertable = false, updatable = false)
    private User owner;

    @JsonView(OrderLineView.OrderLine.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_key", referencedColumnName = "wallet_key", insertable = false, updatable = false)
    private Wallet wallet;

    @Column(name = "proof_name", nullable = true)
    private String proofName;

    @Column(name = "proof_date", nullable = true)
    private LocalDateTime proofDate;

    @Column(name="latitude", nullable = true)
    private double latitude;

    @Column(name="longitude", nullable = true)
    private double longitude;

    @Column(name="proof_small")
    private String proofSmall;

    @Column(name="proof_medium")
    private String proofMedium;

    @Column(name="proof_large")
    private String proofLarge;

    @Column(name="proof_uploaded_datetime")
    private LocalDateTime proofUploadDate;

    @Column(name="transaction_line_fee")
    private Double transactionLineFee;

    @Column(name="transaction_line_vat")
    private Double transactionLineVat;

    @Column(name="loaded_at")
    private LocalDateTime loadedDate;

    @Column(name="orderline_stripe_id")
    private String StripeChargeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batch_key", referencedColumnName = "batch_key", insertable = false, updatable = false)
    private Batch batch;



    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
