package app.repositories.Order;

import app.models.Order.OrderLine;
import app.repositories.interfaces.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class OrderlineRepostory implements CrudRepository<OrderLine, Integer> {

    @Autowired
    private EntityManager em;

    @Override
    public OrderLine save(OrderLine entity) {
        return em.merge(entity);
    }

    @Override
    public OrderLine findById(Integer primaryKey) {
        return em.find(OrderLine.class,primaryKey);
    }

    @Override
    public Iterable<OrderLine> findAll() {
        try {
            return em.createQuery("SELECT o FROM OrderLine o", OrderLine.class).getResultList();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(o) FROM OrderLine o", Long.class).getSingleResult();
    }

    @Override
    public void delete(OrderLine entity) {
        OrderLine toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return em.createQuery("SELECT COUNT(o) FROM OrderLine o WHERE o.id = :id", Long.class).setParameter("id", primaryKey).getSingleResult() > 0;
    }
}
