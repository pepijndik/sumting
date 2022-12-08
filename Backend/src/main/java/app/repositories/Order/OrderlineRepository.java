package app.repositories.Order;

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
    public OrderLine findById(Integer primaryKey) {
        return em.find(OrderLine.class, primaryKey);
    }

    @Override
    public Iterable<OrderLine> findAll() {
        try {
            return em.createQuery("SELECT o FROM OrderLine o", OrderLine.class).getResultList();
        } catch (Exception e) {
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

    /**
     * Gets orderlines by order id and product id, using project id to search for a product id
     *
     * @param clientID the client id
     * @param projectID the project id
     * @return the orderlines found by client and project
     */
    public Iterable<OrderLine> findByClientAndProject(int clientID, int projectID) {
        int productID = em.createQuery("SELECT p.id FROM Project p WHERE p.id = :id", Integer.class).setParameter("id", projectID).getSingleResult();

        return em.createQuery("SELECT o FROM OrderLine o WHERE o.owner.id = :clientID AND o.product.id = :productID", OrderLine.class)
                .setParameter("clientID", clientID)
                .setParameter("productID", productID)
                .getResultList();
    }

    /**
     * Gets orderlines by product id, using project id to search for a product id
     *
     * @param projectID the project id
     * @return the orderlines found by project
     */
    public Iterable<OrderLine> findByProject(int projectID) {
        int productID = em.createQuery("SELECT p.id FROM Project p WHERE p.id = :id", Integer.class).setParameter("id", projectID).getSingleResult();

        return em.createQuery("SELECT o FROM OrderLine o WHERE o.product.id = :productID", OrderLine.class)
                .setParameter("productID", productID)
                .getResultList();
    }

    /**
     * Gets orderlines by client id
     *
     * @param clientID the client id
     * @return the orderlines found by client
     */
    public Iterable<OrderLine> findByClient(int clientID) {
        return em.createQuery("SELECT o FROM OrderLine o WHERE o.owner.id = :clientID", OrderLine.class)
                .setParameter("clientID", clientID)
                .getResultList();
    }
}
