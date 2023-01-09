package app.models.Batch;

import app.models.Country;
import app.models.Identifiable;
import app.views.BatchView;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = BatchInvoice.TABLE_NAME)
public class BatchInvoice  implements Identifiable<Integer> {
    public static final String TABLE_NAME = "batch_invoice";

    @Id
    @JsonView(BatchView.Batch.class)
    @Column(name = "batch_invoice_key", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    @JsonView(BatchView.Batch.class)
    @Column(name = "amount_total", nullable = false)
    private Double amountTotal;
    @JsonView(BatchView.Batch.class)
    @Column(name = "invoice_nr", nullable = false)
    private String invoiceNr;
    @JsonView(BatchView.Batch.class)
    @Column(name = "invoice_unit_price", nullable = false)
    private Double invoiceUnitPrice;

    @OneToOne(mappedBy = "batchInvoice")
    @JsonBackReference
    @JoinColumn(name = "batch_invoice_key", referencedColumnName = "batch_invoice_key", insertable = false, updatable = false)
    private Batch batch;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}
