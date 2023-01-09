package app.models.Project;

import app.models.Order.Order;
import app.views.ProjectView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import jdk.jfr.Timestamp;
import app.models.Identifiable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = Project.TABLE_NAME)
public class Project implements Identifiable<Integer> {
    public static final String TABLE_NAME = "project";

    @Id()
    @JsonView(ProjectView.Overview.class)
    @Column(name = "project_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JsonView(ProjectView.Overview.class)
    @Column(name = "description",columnDefinition = "varchar(255)")
    private String description;

    @Nullable
    @JsonView(ProjectView.Overview.class)
    @Column(name = "description_long", columnDefinition = "TEXT")
    private String description_long;

    @Nullable
    @JsonView(ProjectView.Overview.class)
    @Column(name = "name", columnDefinition = "varchar(255)")
    private String name;

    @Nullable
    @JsonView(ProjectView.Overview.class)
    @Column(name = "latitude", columnDefinition = "varchar(255)")
    private String latitude;

    @Nullable
    @JsonView(ProjectView.Overview.class)
    @Column(name = "longitude", columnDefinition = "varchar(255)")
    private String longitude;


    @Nullable
    @JsonView(ProjectView.Overview.class)
    @Column(name = "project_image_medium", columnDefinition = "char(255)")
    private String image;

    @Timestamp
    @Column(name = "created_at", columnDefinition = "timestamp")
    private LocalDate created_at;

    @Timestamp
    @Column(name = "updated_at", columnDefinition = "timestamp")
    private String updated_at;


    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JsonView(ProjectView.Overview.class)
    @JoinColumn(name = "project_type_key", referencedColumnName = "project_type_key", insertable = false, updatable = false)
    private ProjectType type;


//    @Nullable
//    @JsonIgnore
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "project_key", referencedColumnName = "order_key",
//            insertable = false,
//            updatable = false)
//    private List<Order> orders;
    @Override
    @JsonIgnore
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
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
