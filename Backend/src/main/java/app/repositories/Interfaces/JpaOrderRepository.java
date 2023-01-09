package app.repositories.Interfaces;

import app.models.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, Integer> {


}

