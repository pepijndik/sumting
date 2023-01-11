
package app.repositories.Project;

import app.models.Project.Project;

import app.repositories.Interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * A JPA implementation of a project repository, used to perform crud actions.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    /**
     * The entity manager is used to perform crud actions on the database.
     */
    @Autowired
    private EntityManager em;

    /**
     * This method is used to save a project to the database.
     *
     * @param entity The project to be saved.
     * @return The saved project.
     */
    @Override
    public Project save(Project entity) {
        return em.merge(entity);
    }

    /**
     * This method is used to find a project by its id.
     *
     * @param id The id of the entity to be found.
     * @return The project.
     */
    @Override
    public Optional<Project> findById(Integer id) {
        return Optional.ofNullable(em.find(Project.class, id));
    }

    /**
     * This method is used to delete a project in the database.
     */
    @Override
    public void delete(Project entity) {
        Project toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    /**
     * This method is used to find all projects in the database.
     *
     * @return A list of all the projects.
     */
    @Override
    public boolean existsById(Integer primaryKey) {
        return this.findById(primaryKey).isPresent();
    }
}
