package app.repositories.Interfaces;

import app.models.Project.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This repository is used to interact with the project_type table in the database.
 * By extending the JpaRepository, it is possible to perform crud actions.
 */
@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer> {
}
