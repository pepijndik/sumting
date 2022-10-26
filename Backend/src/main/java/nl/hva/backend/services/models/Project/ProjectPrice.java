package nl.hva.backend.services.models.Project;


import nl.hva.backend.services.models.Identifiable;

import javax.persistence.*;

@Entity
@Table(name = ProjectPrice.TABLE_NAME)
public class ProjectPrice implements Identifiable<Long> {
    public static final String TABLE_NAME = "project_price";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "project_key")
    private Long projectKey;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_key", referencedColumnName = "project_key", insertable = false, updatable = false)
    private Project project;
}
