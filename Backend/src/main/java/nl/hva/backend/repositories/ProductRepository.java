package nl.hva.backend.repositories;

import nl.hva.backend.models.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class ProductRepository implements CrudRepository<Product, Integer> {

    @Autowired
    private EntityManager em;

    @Override
    public Product save(Product entity) {
        return em.merge(entity);
    }

    @Override
    public Product findById(Integer id) {
        return em.find(Product.class,id);
    }

    @Override
    public Iterable<Product> findAll() {
        return em.createQuery("SELECT a FROM Product a", Product.class).getResultList();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Product entity) {
        Product toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return false;
    }
}
