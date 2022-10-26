package nl.hva.backend.models.Project;

import com.sun.istack.Nullable;
import nl.hva.backend.models.Identifiable;

import javax.persistence.*;
@Entity
@Table(name = ProjectType.TABLE_NAME)
public class ProjectType implements Identifiable<Integer> {

    public static final String TABLE_NAME = "project_type";

    @Id
    @Column(name = "project_type_key")
    private Integer id;

    @Nullable
    @Column(name = "description",columnDefinition = "varchar(255)")
    private String description;


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
}
