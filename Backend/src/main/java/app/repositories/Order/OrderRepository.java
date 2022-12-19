package app.repositories.Order;

import app.models.Order.Order;
import app.models.Order.OrderLine;
import app.repositories.interfaces.CrudRepository;
import org.aspectj.weaver.ast.Or;
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
        try {
            return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
        } catch (Exception e){
            e.printStackTrace();
        }
    return null;
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(o) FROM Order o", Long.class).getSingleResult();
    }

    @Override
    public void delete(Order entity) {
        Order toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    @Override
    public boolean existsById(Integer primaryKey) {

        return em.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class)
                .setParameter("id", primaryKey)
                .getResultList()
                .size() > 0;
    }

    public Iterable<Order> findByClient(Integer id){
        return em.createQuery("SELECT DISTINCT o FROM Order o FULL JOIN OrderLine ol ON ol.orderKey = o.id WHERE ol.owner.id = :id", Order.class)
            .setParameter("id", id)
            .getResultList();
    }

    /**
     * Gets orderlines by product id, using project id to search for a product id
     *
     * @param projectID the project id
     * @return the orderlines found by project
     */
    public Iterable<Order> findByProject(Integer projectID) {
        int productID = em.createQuery("SELECT p.id FROM Product p WHERE p.project.id = :id", Integer.class).setParameter("id", projectID).getSingleResult();

        return em.createQuery("SELECT DISTINCT o FROM Order o FULL JOIN OrderLine ol ON ol.orderKey = o.id WHERE ol.product.id = :productID", Order.class)
            .setParameter("productID", productID)
            .getResultList();
    }

    /**
     * Gets orders by client id and product id, using project id to search for a product id
     *
     * @param clientID the client id
     * @param projectID the project id
     * @return the orderlines found by client and project
     */
    public Iterable<Order> findByClientAndProject(Integer clientID, Integer projectID) {
        int productID = em.createQuery("SELECT p.id FROM Product p WHERE p.project.id = :id", Integer.class).setParameter("id", projectID).getSingleResult();

        return em.createQuery("SELECT DISTINCT o FROM Order o INNER JOIN OrderLine ol on ol.orderKey = o.id WHERE ol.owner.id = :clientID AND ol.product.id = :productID", Order.class)
            .setParameter("clientID", clientID)
            .setParameter("productID", productID)
            .getResultList();
    }
}
