package app.models.Batch;

import app.models.Country;
import app.models.Identifiable;
import app.models.Order.OrderLine;
import app.models.Project.Project;
import app.views.BatchView;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = Batch.TABLE_NAME)
public class Batch implements Identifiable<Integer> {
    public static final String TABLE_NAME = "\"batch\"";

    public Batch() {

    }

    public Batch(
            Integer id,
            LocalDateTime createdAt,
            String textPlanned,
            Integer batchSize,
            Integer projectKey
            ) {
        this.id = id;
        this.createdAt = createdAt;
        this.textPlanned = textPlanned;
        this.batchSize = batchSize;
        this.projectKey = projectKey;
    }

    @Id
    @Column(name = "batch_key", nullable = false)
    @JsonView(BatchView.Batch.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(BatchView.Batch.class)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @JsonView(BatchView.Batch.class)
    @Column(name = "text_planned", nullable = true)
    private String textPlanned; // planned text

    @JsonView(BatchView.Batch.class)
    @Column(name = "text_completed", nullable = true)
    private String textCompleted; // completed text


    @JsonView(BatchView.Batch.class)
    @Column(name = "batch_size", nullable = true, columnDefinition = "int default 0")
    private Integer batchSize; // size of batch

    @Column(name = "project_key", nullable = true, columnDefinition = "int default 0")
    private Integer projectKey;

    @Column(name = "batch_invoice_key", nullable = true, columnDefinition = "int default 0")
    private Integer batchInvoiceKey;

    @JsonView(BatchView.Batch.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_key", referencedColumnName = "project_key", insertable = false, updatable = false)
    private Project project;

    /**
     * The batched invoices
     */
    @OneToOne
    @JsonView(BatchView.Batch.class)
    @JsonManagedReference
    @JoinColumn(name = "batch_invoice_key", referencedColumnName = "batch_invoice_key", insertable = false, updatable = false)
    private BatchInvoice batchInvoice;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonView(BatchView.Batch.class)
    @JoinColumn(name = "batch_key", referencedColumnName = "batch_key", insertable = false, updatable = true)
    @JsonManagedReference(value = "batch_orderline")
    private List<OrderLine> orderLines;
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTextPlanned() {
        return textPlanned;
    }

    public void setTextPlanned(String textPlanned) {
        this.textPlanned = textPlanned;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Integer getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(Integer projectKey) {
        this.projectKey = projectKey;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }


}
