package app.models.Order;


import app.models.Batch.Batch;
import app.models.Identifiable;
import app.models.Product.Product;
import app.models.User.User;
import app.models.Wallet;
import app.views.BatchView;
import app.views.OrderLineView;
import app.views.OrderView;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = OrderLine.TABLE_NAME)
public class OrderLine implements Identifiable<Integer> {
    public static final String TABLE_NAME = "orderline_contribution";

    @Id
    @JsonView(OrderLineView.OrderLine.class)
    @Column(name = "orderline_key", nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(OrderLineView.OrderLine.class)
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_key", referencedColumnName = "order_key", insertable = false, updatable = false, nullable = true, columnDefinition = "int")
    private Order order;


    @JsonView(OrderLineView.OrderLine.class)
    @Column(name = "notes", nullable = true)
    private String notes;

    @Nullable
    @JsonView(OrderLineView.OrderLine.class)
    @Column(name = "transaction_line_total", nullable = true, columnDefinition = "double default 0.0")
    private Double transactionLineTotal;

    @JsonView(OrderLineView.OrderLine.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_key", referencedColumnName = "product_key", insertable = false, updatable = false)
    private Product product;

    @Nullable
    @JsonView(OrderLineView.OrderLine.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_key", referencedColumnName = "user_key", insertable = false, updatable = false)
    private User owner;

    @Nullable
    @JsonView(OrderLineView.OrderLine.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_key", referencedColumnName = "wallet_key", insertable = false, updatable = false)
    private Wallet wallet;

    @Nullable
    @Column(name = "proof_name", nullable = true)
    private String proofName;

    @Nullable
    @Column(name = "proof_date", nullable = true)
    private LocalDateTime proofDate;

    @Nullable
    @JsonView(OrderLineView.OrderLine.class)
    @Column(name="latitude", nullable = true, columnDefinition = "double default 0.0")
    private Double latitude;

    @Nullable
    @JsonView(OrderLineView.OrderLine.class)
    @Column(name="longitude", nullable = true, columnDefinition = "double default 0.0")
    private Double longitude;

    @Nullable
    @Column(name="proof_small",nullable = true)
    private String proofSmall;

    @Nullable
    @Column(name="proof_medium",nullable = true)
    private String proofMedium;

    @Nullable
    @Column(name="proof_large",nullable = true)
    private String proofLarge;

    @Nullable
    @Column(name="proof_uploaded_datetime",nullable = true)
    private LocalDateTime proofUploadDate;

    @Nullable
    @Column(name="transaction_line_fee",nullable = true)
    private Double transactionLineFee;

    @Nullable
    @Column(name="transaction_line_vat",nullable = true)
    private Double transactionLineVat;

    @Nullable
    @Column(name="loaded_at",nullable = true)
    private LocalDateTime loadedDate;

    @Nullable
    @JsonView(OrderLineView.OrderLine.class)
    @Column(name="orderline_stripe_id",nullable = true)
    private String StripeChargeId;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    @JsonView(BatchView.Batch.class)
    @JsonBackReference("batch_orderline")
    @JoinColumn(name = "batch_key", referencedColumnName = "batch_key", insertable = false, updatable = true)
    private Batch batch;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }


    public String getNotes() {
        return notes;
    }

    public Double getTransactionLineTotal() {
        return transactionLineTotal;
    }

    public Product getProduct() {
        return product;
    }

    public User getOwner() {
        return owner;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getProofName() {
        return proofName;
    }

    public LocalDateTime getProofDate() {
        return proofDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getProofSmall() {
        return proofSmall;
    }

    public String getProofMedium() {
        return proofMedium;
    }

    public String getProofLarge() {
        return proofLarge;
    }

    public LocalDateTime getProofUploadDate() {
        return proofUploadDate;
    }

    public Double getTransactionLineFee() {
        return transactionLineFee;
    }

    public Double getTransactionLineVat() {
        return transactionLineVat;
    }

    public LocalDateTime getLoadedDate() {
        return loadedDate;
    }

    public String getStripeChargeId() {
        return StripeChargeId;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTransactionLineTotal(Double transactionLineTotal) {
        this.transactionLineTotal = transactionLineTotal;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }

    public void setProofDate(LocalDateTime proofDate) {
        this.proofDate = proofDate;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setProofSmall(String proofSmall) {
        this.proofSmall = proofSmall;
    }

    public void setProofMedium(String proofMedium) {
        this.proofMedium = proofMedium;
    }

    public void setProofLarge(String proofLarge) {
        this.proofLarge = proofLarge;
    }

    public void setProofUploadDate(LocalDateTime proofUploadDate) {
        this.proofUploadDate = proofUploadDate;
    }

    public void setTransactionLineFee(Double transactionLineFee) {
        this.transactionLineFee = transactionLineFee;
    }

    public void setTransactionLineVat(Double transactionLineVat) {
        this.transactionLineVat = transactionLineVat;
    }

    public void setLoadedDate(LocalDateTime loadedDate) {
        this.loadedDate = loadedDate;
    }

    public void setStripeChargeId(String stripeChargeId) {
        StripeChargeId = stripeChargeId;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }


}
