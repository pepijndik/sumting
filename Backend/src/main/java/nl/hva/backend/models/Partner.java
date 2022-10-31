package nl.hva.backend.models;

import nl.hva.backend.models.Project.Project;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = Partner.TABLE_NAME)
public class Partner implements Identifiable<Integer>{

    public static final String TABLE_NAME = "partner";

    @Id()
    @Column(name = "partner_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "country", columnDefinition = "int", nullable = true,updatable = false,insertable = false)
    private Country country;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "zipcode", nullable = true)
    private String zipcode;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}
