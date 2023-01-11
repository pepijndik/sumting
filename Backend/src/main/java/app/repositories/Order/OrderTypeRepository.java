package app.repositories.Order;

import app.models.Order.OrderType;
import org.springframework.data.repository.CrudRepository;

/**
 * This repository is used to perform crud actions on the order type table in the database.
 */
public interface OrderTypeRepository extends CrudRepository<OrderType, Integer> {
}
