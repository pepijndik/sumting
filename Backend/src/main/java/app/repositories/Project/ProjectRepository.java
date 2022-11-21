package app.repositories.Project;

import app.models.Project.Project;
import app.repositories.interfaces.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
        return em.createQuery("SELECT a FROM Project a", Project.class).getResultList();
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
