package app.models.Product;

import app.models.Identifiable;
import app.models.Project.Project;
import app.views.ProductView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = Product.TABLE_NAME)
public class Product implements Identifiable<Integer> {

    public static final String TABLE_NAME = "product";

    @Id
    @JsonView(ProductView.Overview.class)
    @Column(name = "product_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name", nullable = false)

    @JsonView(ProductView.Overview.class)
    private String name;

    @Column(name = "description", nullable = true)

    @JsonView(ProductView.Overview.class)
    private String description;


    @JsonView(ProductView.Overview.class)
    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private double price;

    @Column(name = "product_stripe_id", nullable = true)

    @JsonView(ProductView.Overview.class)
    private String stripe_id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JsonView(ProductView.Overview.class)
    @JoinColumn(name = "project_key", columnDefinition = "int", nullable = true, updatable = false, insertable = false)
    private Project project;

    @OneToOne(cascade = CascadeType.DETACH)

    @JsonView(ProductView.Overview.class)
    @JoinColumn(name = "product_type_key", columnDefinition = "int", nullable = true, updatable = false, insertable = false)
    private ProductType type;


    @JsonView(ProductView.Overview.class)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    @JsonView(ProductView.Overview.class)
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "product_id_ext", nullable = true)
    private Integer id_ext;

    @JsonView(ProductView.Overview.class)
    @Column(name = "is_active", nullable = true)
    private boolean active;

    @Override
    @JsonIgnore
    public Integer getId() {

        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStripe_id() {
        return stripe_id;
    }

    public void setStripe_id(String stripe_id) {
        this.stripe_id = stripe_id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getId_ext() {
        return id_ext;
    }

    public void setId_ext(Integer id_ext) {
        this.id_ext = id_ext;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
