package nl.hva.backend.repositories;

import nl.hva.backend.models.Order.Order;
import nl.hva.backend.models.Project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class OrderRepository implements CrudRepository<Order, Integer> {


    @Autowired
    private EntityManager em;

    @Override
    public Order save(Order entity) {
        return em.merge(entity);
    }

    @Override
    public Order findById(Integer id) {
        return em.find(Order.class,id);
    }

    @Override
    public Iterable<Order> findAll() {
        return em.createQuery("SELECT a FROM Order a", Order.class).getResultList();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Order entity) {
        Order toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return false;
    }
}