package nl.hva.backend.models;

import com.sun.istack.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "pr_project")
public class Project implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotNull
    @Column(name = "description", nullable = false)
    private String title;

    @Column(name = "description_long", columnDefinition = "TEXT")
    private String description;

    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
