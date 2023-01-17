package app.repositories;

import app.models.Product.Product;
import app.repositories.Interfaces.CustomCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * This repository is used to perform crud actions on the product table in the database.
 */
@Repository
@Transactional
public class ProductRepository implements CustomCrudRepository<Product, Integer> {

    /**
     * The entity manager is used to perform crud actions on the database.
     */
    @Autowired
    private EntityManager em;

    /**
     * This method is used to save a product to the database.
     *
     * @param entity The product to be saved.
     * @return The saved product.
     */
    @Override
    public Product save(Product entity) {
        return em.merge(entity);
    }

    /**
     * This method is used to find a product by its id.
     *
     * @param id The id of the entity to be found.
     * @return The product.
     */
    @Override
    public Product findById(Integer id) {
        return em.find(Product.class,id);
    }

    /**
     * This method is used to find all products in the database.
     *
     * @return A list of all the products.
     */
    @Override
    public Iterable<Product> findAll() {
        return em.createQuery("SELECT a FROM Product a", Product.class).getResultList();
    }

    /**
     * This method is used to count the amount of products in the database.
     *
     * @return The amount of products in the database.
     */
    public Iterable<Product> findByProjectId(int projectId) {
        return em.createQuery("SELECT a FROM Product a WHERE project.id = :id", Product.class)
                .setParameter("id", projectId).getResultList();
    }

    /**
     * This method is used to count the amount of products in the database.
     *
     * @return The amount of products in the database.
     */
    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(a) FROM Product a", Long.class).getSingleResult();
    }

    /**
     * This method is used to delete a product from the database.
     *
     * @param entity The product to be deleted.
     */
    @Override
    public void delete(Product entity) {
        Product toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    /**
     * This method is used to delete a product from the database by its id.
     *
     * @param primaryKey The id of the product to be deleted.
     */
    @Override
    public boolean existsById(Integer primaryKey) {
        return em.find(Product.class, primaryKey) != null;
    }
}
