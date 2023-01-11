package app.repositories.Order;

import app.models.Order.Order;
import app.repositories.Interfaces.CustomCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * This repository is used to perform crud actions on the order table in the database.
 */
@Repository
@Transactional
public class OrderRepository implements CustomCrudRepository<Order, Integer> {

    /**
     * The entity manager is used to perform crud actions on the database.
     */
    @Autowired
    private EntityManager em;

    /**
     * This method is used to save an order to the database.
     *
     * @param entity The order to be saved.
     * @return The saved order.
     */
    @Override
    public Order save(Order entity) {
        return em.merge(entity);
    }

    /**
     * This method is used to find an order by its id.
     *
     * @param id The id of the entity to be found.
     * @return The order.
     */
    @Override
    public Order findById(Integer id) {
        return em.find(Order.class, id);
    }

    /**
     * This method is used to find all orders in the database.
     *
     * @return A list of all the orders.
     */
    @Override
    public Iterable<Order> findAll() {
        try {
            return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to count the number of orders in the database.
     *
     * @return The number of orders.
     */
    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(o) FROM Order o", Long.class).getSingleResult();
    }

    /**
     * This method is used to delete an order from the database.
     *
     * @param entity The order to be deleted.
     */
    @Override
    public void delete(Order entity) {
        Order toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    /**
     * This method is used to check if an order exists in the database.
     *
     * @param primaryKey The id of the order to be checked.
     * @return True if the order exists, false otherwise.
     */
    @Override
    public boolean existsById(Integer primaryKey) {

        return em.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class)
            .setParameter("id", primaryKey)
            .getResultList()
            .size() > 0;
    }

    /**
     * This method is used to find all orders by a specific user.
     * @author Colin Laan
     *
     * @param id The id of the user.
     * @return A list of all the orders linked to the user.
     */
    public Iterable<Order> findByClient(Integer id) {
        return em.createQuery("SELECT DISTINCT o FROM Order o FULL JOIN OrderLine ol ON ol.id = o.id WHERE ol.owner.id = :id", Order.class)
            .setParameter("id", id)
            .getResultList();
    }

    /**
     * Gets orderlines by product id, using project id to search for a product id
     * @author Colin Laan
     *
     * @param projectID the project id
     * @return the orderlines found by project
     */
    public Iterable<Order> findByProject(Integer projectID) {
        int productID = em.createQuery("SELECT p.id FROM Product p WHERE p.project.id = :id", Integer.class).setParameter("id", projectID).getSingleResult();

        return em.createQuery("SELECT DISTINCT o FROM Order o FULL JOIN OrderLine ol ON ol.id = o.id WHERE ol.product.id = :productID", Order.class)
            .setParameter("productID", productID)
            .getResultList();
    }

    /**
     * Gets orders by client id and product id, using project id to search for a product id
     * @author Colin Laan
     *
     * @param clientID  the client id
     * @param projectID the project id
     * @return the orderlines found by client and project
     */
    public Iterable<Order> findByClientAndProject(Integer clientID, Integer projectID) {
        int productID = em.createQuery("SELECT p.id FROM Product p WHERE p.project.id = :id", Integer.class).setParameter("id", projectID).getSingleResult();

        return em.createQuery("SELECT DISTINCT o FROM Order o INNER JOIN OrderLine ol on ol.id = o.id WHERE ol.owner.id = :clientID AND ol.product.id = :productID", Order.class)
            .setParameter("clientID", clientID)
            .setParameter("productID", productID)
            .getResultList();
    }
}
