package nl.hva.backend.models;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import jdk.jfr.Timestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = Project.TABLE_NAME)
public class Project implements Identifiable<Long> {
    public static final String TABLE_NAME = "pr_project";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_key")
    private Long id;

    @Nullable
    @Column(name = "description",columnDefinition = "varchar(255)")
    private String description;

    @Nullable
    @Column(name = "description_long", columnDefinition = "TEXT")
    private String description_long;

    @Nullable
    @Column(name = "latitude", columnDefinition = "varchar(255)")
    private String latitude;

    @Nullable
    @Column(name = "longitude", columnDefinition = "varchar(255)")
    private String longitude;

    @Nullable
    @Column(name = "fee", columnDefinition = "double")
    private Double fee;

    @Nullable
    @Column(name = "project_image_medium", columnDefinition = "char(255)")
    private String image;

    @Timestamp
    @Column(name = "created_at", columnDefinition = "timestamp")
    private LocalDate created_at;

    @Timestamp
    @Column(name = "updated_at", columnDefinition = "timestamp")
    private String updated_at;

    @Nullable
    @Column(name = "unit_price", columnDefinition = "double")
    private Double unitPrice;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }




    public String getDescription_long() {
        return this.description_long;
    }

    public void setDescription_long(String description_long) {
        this.description_long = description_long;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        System.out.println(id);
        this.id = id;
    }
}
