package app.repositories.Batch;

import app.models.Batch.Batch;
import app.repositories.Interfaces.CustomCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class BatchRepository implements CustomCrudRepository<Batch, Integer> {

    @Autowired
    private EntityManager em;

    @Override
    public Batch save(Batch entity) {
        return em.merge(entity);
    }

    @Override
    public Batch findById(Integer primaryKey) {
        return em.find(Batch.class,primaryKey);
    }

    @Override
    public Iterable<Batch> findAll() {
        return em.createQuery("SELECT b FROM Batch b", Batch.class).getResultList();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Batch entity) {
        Batch toRemove = em.merge(entity);
        toRemove.setProject(null);
        em.remove(toRemove);
    }

    @Override
    public boolean existsById(Integer primaryKey) {
         return this.findById(primaryKey) != null;
    }
}
