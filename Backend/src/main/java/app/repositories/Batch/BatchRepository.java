package app.repositories.Batch;

import app.models.Batch.Batch;
import app.repositories.Interfaces.CustomCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * This repository is ued to interact with the batch table in the database.
 * By implementing and using the EntityManager, it is possible to perform crud actions.
 */
@Repository
@Transactional
public class BatchRepository implements CustomCrudRepository<Batch, Integer> {

    /**
     * The EntityManager is used to interact with the database.
     */
    @Autowired
    private EntityManager em;

    /**
     * This method is used to create a new batch in the database.
     *
     * @param entity The batch to be created.
     * @return The created batch.
     */
    @Override
    public Batch save(Batch entity) {
        return em.merge(entity);
    }

    /**
     * This method is used to find a batch in the database.
     *
     * @param primaryKey The id of the batch to be found.
     * @return The found batch.
     */
    @Override
    public Batch findById(Integer primaryKey) {
        return em.find(Batch.class, primaryKey);
    }

    /**
     * This method is used to find all the batches in the database.
     *
     * @return An Iterable of all the batches
     */
    @Override
    public Iterable<Batch> findAll() {
        return em.createQuery("SELECT b FROM Batch b", Batch.class).getResultList();
    }

    /**
     * This method is used to get the count of batches to be found in the database.
     *
     * @return The amount of batches that can be found in the database
     */
    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(b) FROM Batch b", Long.class).getSingleResult();
    }

    /**
     * This method is used to delete a batch from the database.
     *
     * @param entity The batch to be deleted.
     */
    @Override
    public void delete(Batch entity) {
        Batch toRemove = em.merge(entity);
        toRemove.setProject(null);
        em.remove(toRemove);
    }

    /**
     * This method is used to find out if a certain batch exists in the database.
     *
     * @param primaryKey The id of the batch to look for.
     * @return Whether the batch is found or not
     */
    @Override
    public boolean existsById(Integer primaryKey) {
        return this.findById(primaryKey) != null;
    }
}
