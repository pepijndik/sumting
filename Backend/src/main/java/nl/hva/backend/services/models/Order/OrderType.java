package nl.hva.backend.services.models.Order;

import com.sun.istack.Nullable;
import nl.hva.backend.services.models.Identifiable;
import javax.persistence.*;

@Entity
@Table(name = OrderType.TABLE_NAME)
public class OrderType implements Identifiable<Integer> {

    public static final String TABLE_NAME = "order_type";

    @Id
    @Column(name = "order_type_key")
    private Integer id;

    @Nullable
    @Column(name = "description",columnDefinition = "varchar")
    private String description;

    @Nullable
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

