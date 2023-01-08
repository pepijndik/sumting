package app.repositories.Order;

import app.models.Order.OrderLine;

import org.springframework.beans.factory.annotation.Autowired;
import app.repositories.Interfaces.CustomCrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrderlineRepository implements CustomCrudRepository<OrderLine, Integer> {

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
            return null;
        }
    }
    public List<OrderLine> findAllById(Iterable<Integer> integers) {

        return  em.createQuery("SELECT o FROM OrderLine o WHERE o.id = :id", OrderLine.class).getResultList();
    }

    public List<OrderLine> findAllById(Integer id) {
       try {
            return em.createQuery("SELECT o FROM OrderLine o WHERE o.id = :id", OrderLine.class).getResultList();
        }catch (Exception e){
            System.out.println(e);
        }
       return null;
    }


    public Iterable<OrderLine> findAllBy(Integer product_id, Integer order_id) {
        try {
             if (product_id != null && order_id == null) {
                 TypedQuery<OrderLine> query =
                         em.createQuery("SELECT o FROM OrderLine o " +
                                 "WHERE o.batch IS NULL AND o.product.id = :pId", OrderLine.class);
                 query.setParameter("pId", product_id);
                 return query.getResultList();
             } else if (order_id != null && product_id == null) {
                 TypedQuery<OrderLine> query =
                         em.createQuery("SELECT o FROM OrderLine o WHERE o.order.id = :oId", OrderLine.class);
                 query.setParameter("oId", order_id);
                 return query.getResultList();
             }
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
        return this.findById(primaryKey) != null;
    }
}
