package nl.hva.backend.repositories;

import nl.hva.backend.models.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository implements EntityRepository<Project> {


    @Override
    public Project save(Project entity) {
        return null;
    }

    @Override
    public Project findById(Long id) {
        return null;
    }

    @Override
    public Project update(Project entity) {
        return null;
    }

    @Override
    public void delete(Project entity) {

    }

    @Override
    public List<Project> findAll() {
        return null;
    }
}
