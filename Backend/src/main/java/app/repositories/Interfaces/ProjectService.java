package app.repositories.Interfaces;

import app.models.Project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Project save(Project entity);

    Optional<Project> findById(Integer id);

    void delete(Project entity);

    boolean existsById(Integer primaryKey);
}
