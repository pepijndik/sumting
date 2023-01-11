package app.repositories.Interfaces;

import app.models.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This repository is used to interact with the order table in the database.
 * By extending the JpaRepository, it is possible to perform crud actions.
 */
@Repository
public interface JpaOrderRepository extends JpaRepository<Order, Integer> {


}

