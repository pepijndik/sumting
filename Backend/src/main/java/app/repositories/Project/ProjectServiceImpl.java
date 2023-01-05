
package app.repositories.Project;

import app.advSearch.SearchCriteria;
import app.models.Project.Project;

import app.repositories.Interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    private EntityManager em;

    @Override
    public Project save(Project entity) {
        return em.merge(entity);
    }

    @Override
    public Optional<Project> findById(Integer id) {
        return Optional.ofNullable(em.find(Project.class, id));
    }


    @Override
    public void delete(Project entity) {
        Project toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return this.findById(primaryKey) != null;
    }
}
