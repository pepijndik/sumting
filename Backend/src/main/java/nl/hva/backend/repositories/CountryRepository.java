package nl.hva.backend.repositories;

import nl.hva.backend.models.Country;
import nl.hva.backend.models.Project.Project;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CountryRepository implements CrudRepository<Country, Integer>{

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