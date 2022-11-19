package app.repositories;

import app.models.Country;
import app.repositories.Interfaces.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CountryRepository implements CrudRepository<Country, Integer> {

    @Autowired
    private EntityManager em;

    @Override
    public Country save(Country entity) {
        return em.merge(entity);
    }

    @Override
    public Country findById(Integer key) {
        return em.find(Country.class,key);
    }

    @Override
    public Iterable<Country> findAll() {
        return em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Country entity) {

    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return this.findById(primaryKey) != null;
    }
}
