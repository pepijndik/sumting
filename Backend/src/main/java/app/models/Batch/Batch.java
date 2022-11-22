package app.models.Batch;

import app.models.Country;
import app.models.Identifiable;
import app.models.Project.Project;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = Country.TABLE_NAME)
public class Batch implements Identifiable<Integer> {
    public static final String TABLE_NAME = "batch";

    @Id
    @Column(name = "batch_key", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "text_planned", nullable = true)
    private String textPlanned; // planned text

    @Column(name = "text_completed", nullable = true)
    private String textCompleted; // completed text

    @Column(name = "status", nullable = true)
    private String status; // status of batch

    @Column(name = "batch_size", nullable = true, columnDefinition = "int default 0")
    private Integer batchSize; // size of batch

    @Column(name = "project_key", nullable = true, columnDefinition = "int default 0")
    private Integer projectKey;

    @Column(name = "batch_invoice_key", nullable = true, columnDefinition = "int default 0")
    private Integer batchInvoiceKey;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_key", referencedColumnName = "project_key", insertable = false, updatable = false)
    private Project project;

    /**
     * The batched invoices
     */
    @ManyToMany
    private List<BatchInvoice> batchInvoices;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}
