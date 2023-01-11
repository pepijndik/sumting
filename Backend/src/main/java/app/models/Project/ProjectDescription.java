package app.models.Project;

import app.models.Identifiable;
import app.views.ProjectView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import jdk.jfr.Timestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = ProjectDescription.TABLE_NAME)
public class ProjectDescription implements Identifiable<Integer> {

    public static final String TABLE_NAME = "project";

    @Id()
    @JsonView(ProjectView.Overview.class)
    @Column(name = "project_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public ProjectDescription(){

    }
    public ProjectDescription(String description_long) {
        this.description_long = description_long;
    }

    @Nullable
    @JsonView(ProjectView.Overview.class)
    @Column(name = "description_long", columnDefinition = "TEXT")
    private String description_long;

    @Override
    @JsonIgnore
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription_long() {
        return this.description_long;
    }

    public void setDescription_long(String description_long) {
        this.description_long = description_long;
    }
}
