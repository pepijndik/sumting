package app.models;

import app.models.User.User;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = Wallet.TABLE_NAME)
public class Wallet implements Identifiable<Integer>{
    public static final String TABLE_NAME = "\"wallet\"";

    public Wallet() {
    }

    @Id
    @Column(name = "wallet_key", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "balance", nullable = true, columnDefinition = "decimal(10,2)")
    private double balance;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @JoinColumn(name = "payer_user_key", nullable = false, insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.DETACH)
    private User payer;

    @ColumnDefault("'0'")
    @Column(name = "total_orderlines", nullable = false)
    private int totalOrderlines;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "owner_user_key", columnDefinition = "int", nullable = true,updatable = false,insertable = false)
    private User owner;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id =id;
    }
}
