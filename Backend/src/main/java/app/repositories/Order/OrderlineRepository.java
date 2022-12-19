package app.repositories.Order;

import app.models.Order.OrderLine;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderlineRepository extends CrudRepository<OrderLine, Integer> {

}