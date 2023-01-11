package app.repositories.Interfaces;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * This interface is used to create a custom repository.
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface CustomCrudRepository<T, ID> extends Repository<T, ID> {

    /**
     * A method to save an entity to the database.
     *
     * @param entity the entity to be saved.
     * @return the saved entity.
     */
    T save(T entity);

    /**
     * A method to find an entity by its id.
     *
     * @param primaryKey the id of the entity to be found.
     * @return the found entity.
     */
    T findById(ID primaryKey);

    /**
     * A method to return all entities to be found in the database.
     *
     * @return the found entities.
     */
    Iterable<T> findAll();

    /**
     * A method to get the count of entities to be found in the database.
     *
     * @return the count of entities.
     */
    long count();

    /**
     * A method to delete an entity from the database.
     *
     * @param entity the entity to be deleted.
     */
    void delete(T entity);

    /**
     * A method to check if an entity exists in the database.
     *
     * @param primaryKey The primary key of the entity to look for
     * @return Whether the entity exists in the database or not
     */
    boolean existsById(ID primaryKey);

}