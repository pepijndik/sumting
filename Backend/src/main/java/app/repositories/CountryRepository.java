package app.repositories;

import app.models.Country;
import app.repositories.Interfaces.CustomCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * A JPA implementation of a country repository, used to perform crud actions on the country table in the database.
 */
@Repository
@Transactional
public class CountryRepository implements CustomCrudRepository<Country, Integer> {

    /**
     * The entity manager is used to perform crud actions on the database.
     */
    @Autowired
    private EntityManager em;

    /**
     * This method is used to save a country to the database.
     *
     * @param entity The country to be saved.
     * @return The saved country.
     */
    @Override
    public Country save(Country entity) {
        return em.merge(entity);
    }

    /**
     * This method is used to find a country by its id.
     *
     * @param key The id of the entity to be found.
     * @return The country.
     */
    @Override
    public Country findById(Integer key) {
        return em.find(Country.class,key);
    }

    /**
     * This method is used to find all countries in the database.
     *
     * @return A list of all the countries.
     */
    @Override
    public Iterable<Country> findAll() {
        return em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
    }

    /**
     * This method is used to count the amount of countries in the database.
     *
     * @return The amount of countries in the database.
     */
    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(c) FROM Country c", Long.class).getSingleResult();
    }

    /**
     * This method is used to delete a country from the database.
     *
     * @param entity The country to be deleted.
     */
    @Override
    public void delete(Country entity) {
        Country country = em.merge(entity);
        em.remove(country);
    }

    /**
     * This method is used to check if a country exists in the database.
     *
     * @param primaryKey The id of the country to look for.
     */
    @Override
    public boolean existsById(Integer primaryKey) {
        return this.findById(primaryKey) != null;
    }
}
