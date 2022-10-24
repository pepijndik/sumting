package nl.hva.backend.repositories;

import nl.hva.backend.models.Project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
@Transactional
public class ProjectRepository implements CrudRepository<Project, Integer> {


    @Autowired
    private EntityManager em;

    @Override
    public Project save(Project entity) {
        return em.merge(entity);
    }

    @Override
    public Project findById(Integer id) {
        return em.find(Project.class,id);
    }

    @Override
    public Iterable<Project> findAll() {
        TypedQuery<Project> query = (TypedQuery<Project>) em.createQuery("SELECT u FROM "+Project.TABLE_NAME+" u");

        return query.getResultList();

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Project entity) {
        Project toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return false;
    }
}
