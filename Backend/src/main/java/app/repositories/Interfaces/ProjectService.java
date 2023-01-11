package app.repositories.Interfaces;

import app.models.Project.Project;

import java.util.Optional;

/**
 * This repository is used to interact with the project table in the database.
 */
public interface ProjectService {

    /**
     * A method to save a project to the database.
     * @param entity the project to be saved.
     * @return the saved project
     */
    Project save(Project entity);

    /**
     * A method to find a project by its id.
     * @param id the id of the project to be found.
     * @return the found project.
     */
    Optional<Project> findById(Integer id);

    /**
     * A method to delete a project from the database.
     * @param entity The project to delete from the database/
     */
    void delete(Project entity);

    boolean existsById(Integer primaryKey);
}
