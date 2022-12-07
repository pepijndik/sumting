package app.repositories.Order;

import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.repositories.interfaces.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class OrderlineRepository implements CrudRepository<OrderLine, Integer> {

    @Autowired
    private EntityManager em;

    @Override
    public OrderLine save(OrderLine entity) {
        return em.merge(entity);
    }

    @Override
    public OrderLine findById(Integer id) {
        return em.find(OrderLine.class,id);
    }

    @Override
    public Iterable<OrderLine> findAll() {
        try {
            return em.createQuery("SELECT o FROM OrderLine o", OrderLine.class).getResultList();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(OrderLine entity) {

    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return false;
    }
}
