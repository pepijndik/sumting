package app.models;

import app.models.Project.Project;
import app.models.Project.ProjectType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = Product.TABLE_NAME)
public class Product implements Identifiable<Integer>
{

    public static final String TABLE_NAME = "product";

    @Id()
    @Column(name = "product_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private double price;


    @Column(name = "product_stripe_id", nullable = true)
    private String stripe_id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "project_key", columnDefinition = "int", nullable = true,updatable = false,insertable = false)
    private Project project;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_type_key", columnDefinition = "int", nullable = true,updatable = false,insertable = false)
    private ProjectType type;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "product_id_ext", nullable = true)
    private Integer id_ext;

    @Column(name = "is_active", nullable = true)
    private boolean active;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}
