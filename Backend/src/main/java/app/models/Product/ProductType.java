package app.models.Product;

import com.sun.istack.Nullable;
import app.models.Identifiable;

import javax.persistence.*;

@Entity
@Table(name = ProductType.TABLE_NAME)
public class ProductType implements Identifiable<Integer> {

    public static final String TABLE_NAME = "product_type";

    @Id
    @Column(name = "product_type_key")
    private Integer id;

    @Nullable
    @Column(name = "description",columnDefinition = "varchar(255)")
    private String description;

    @Nullable
    @Column(name = "product_type_name", columnDefinition = "varchar(255)")
    private String product_type_name;

    @Nullable
    @Column(name = "frequency", columnDefinition = "varchar(255)")
    private String frequency;

    @Nullable
    @Column(name = "created_at", columnDefinition = "datetime")
    private String created_at;

    public String getDescription() {
        return description;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id= id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduct_type_name() {
        return product_type_name;
    }

    public void setProduct_type_name(String product_type_name) {
        this.product_type_name = product_type_name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}