package app.repositories;

import app.models.Product.Product;
import app.repositories.Interfaces.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
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

    public Iterable<Product> findByProjectId(int projectId) {
        return em.createQuery("SELECT a FROM Product a WHERE project_key = :id", Product.class)
                .setParameter("id", projectId).getResultList();
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
