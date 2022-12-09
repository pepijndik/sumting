package app.repositories.Order;

import app.models.Order.OrderType;
import org.springframework.data.repository.CrudRepository;


public interface OrderTypeRepository extends CrudRepository<OrderType, Integer> {
}
