package nl.hva.backend.models.Project;

import com.sun.istack.Nullable;
import nl.hva.backend.models.Identifiable;

import javax.persistence.*;
@Entity
@Table(name = ProjectType.TABLE_NAME)
public class ProjectType implements Identifiable<Long> {

    public static final String TABLE_NAME = "project_type";

    @Id
    @Column(name = "project_type_key")
    private Long id;

    @Nullable
    @Column(name = "description",columnDefinition = "varchar(255)")
    private String description;



    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id= id;
    }
}
