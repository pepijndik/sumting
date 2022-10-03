package nl.hva.backend.models;

import javax.persistence.*;

@Entity
@Table(name = "pr_project")
public class User implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
