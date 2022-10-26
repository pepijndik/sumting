package nl.hva.backend.models.Order;

import com.sun.istack.Nullable;
import nl.hva.backend.models.Identifiable;
import javax.persistence.*;

@Entity
@Table(name = nl.hva.backend.models.Project.ProjectType.TABLE_NAME)
public class OrderType implements Identifiable<Long> {

    public static final String TABLE_NAME = "order_type";

    @Id
    @Column(name = "order_type_key")
    private Long id;

    @Nullable
    @Column(name = "description",columnDefinition = "varchar")
    private String description;

    @Nullable
    @Column(name = "type", columnDefinition = "varchar(255)")
    private String type;

    @Nullable
    @Column(name = "is_ext", columnDefinition = "bit")
    private Integer isExt;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
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

