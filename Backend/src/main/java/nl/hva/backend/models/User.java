package nl.hva.backend.models;

import javax.persistence.*;

@Entity
@Table(name = "pr_project")
public class User implements Identifiable<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer findById(Integer id) {
        return null;
    }
}
