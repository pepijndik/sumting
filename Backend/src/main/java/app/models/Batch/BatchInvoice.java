package app.models.Batch;

import app.models.Country;
import app.models.Identifiable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = Country.TABLE_NAME)
public class BatchInvoice  implements Identifiable<Integer> {
    public static final String TABLE_NAME = "batch_invoice";

    @Id
    @Column(name = "batch_invoice_key", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    @Column(name = "amount_total", nullable = false)
    private Double amountTotal;

    @Column(name = "invoiceNr", nullable = false)
    private String invoiceNr;

    @Column(name = "invoice_unit_price", nullable = false)
    private Double invoiceUnitPrice;

    @ManyToMany
    private List<Batch> batches;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}
