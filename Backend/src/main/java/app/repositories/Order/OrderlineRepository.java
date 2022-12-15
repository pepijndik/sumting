package app.repositories.Order;

import app.models.Order.Order;
import app.models.Order.OrderLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderlineRepository extends CrudRepository<OrderLine, Integer> {

}
