package app.models.Order;

import app.views.OrderView;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import app.models.Identifiable;

import javax.persistence.*;

@Entity
@Table(name = OrderType.TABLE_NAME)
public class OrderType implements Identifiable<Integer> {

    public static final String TABLE_NAME = "order_type";


    public OrderType() {
    }
    public OrderType(Integer id, String description, String type) {
        super();
        this.id = id;
        this.description = description;
        this.type = type;
    }
    @Id
    @JsonView(OrderView.Order.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_type_key")
    private Integer id;

    @Nullable
    @Column(name = "description",columnDefinition = "varchar")
    private String description;

    @Nullable
    @JsonView(OrderView.Order.class)
    @Column(name = "order_type", columnDefinition = "varchar(255)")
    private String type;

    @Nullable
    @Column(name = "is_ext", columnDefinition = "bit")
    private Integer isExt;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id= id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsExt() {
        return isExt;
    }

    public void setIsExt(Integer isExt) {
        this.isExt = isExt;
    }
}

