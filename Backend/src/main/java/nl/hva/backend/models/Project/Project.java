package nl.hva.backend.models.Project;

import com.sun.istack.Nullable;
import jdk.jfr.Timestamp;
import nl.hva.backend.models.Identifiable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = Project.TABLE_NAME)
public class Project implements  Identifiable<Integer> {
    public static final String TABLE_NAME = "project";

    @Id()
    @Column(name = "project_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Project(){

    }
    public Project(Integer id, String description, String description_long, String latitude,String longitude) {
        super();
        this.id = id;
        this.description = description;
        this.description_long = description_long;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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
    @Column(name = "project_image_medium", columnDefinition = "char(255)")
    private String image;

    @Timestamp
    @Column(name = "created_at", columnDefinition = "timestamp")
    private LocalDate created_at;

    @Timestamp
    @Column(name = "updated_at", columnDefinition = "timestamp")
    private String updated_at;


    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ProjectType type;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        System.out.println("Setted id" +id);
        this.id = id;
    }

    public ProjectType setType(ProjectType type) {
        return this.type = type;
    }
    public ProjectType getType() {
        return type;
    }
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


}
