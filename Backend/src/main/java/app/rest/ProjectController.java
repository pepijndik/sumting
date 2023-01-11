package app.rest;

import app.exceptions.ModelNotFound;
import app.models.Project.Project;
import app.repositories.Interfaces.ProjectRepository;
import app.repositories.Project.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * Handles the projects related requests
 *
 * @author Pepijn dik
 * @version 1.0
 */
@Controller
public class ProjectController {

    private final ProjectServiceImpl projectServiceImpl;

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectServiceImpl projectServiceImpl, ProjectRepository projectRepository) {
        this.projectServiceImpl = projectServiceImpl;
        this.projectRepository = projectRepository;
    }

    /**
     * Returns a list of all projects
     * @param page page number
     * @param size size of projects
     * @param all no pagination option
     * @return ResponseEntity
     */
    @GetMapping("/projects")
    public ResponseEntity<?> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean all) {
        try {
            if (!all) {
                List<Project> projects;
                Pageable paging = PageRequest.of(page, size);
                Page<Project> pageProjects = projectRepository.findAll(paging);
                projects = pageProjects.getContent();
                Map<String, Object> response = new HashMap<>();
                response.put("projects", projects);
                response.put("currentPage", pageProjects.getNumber());
                response.put("totalItems", pageProjects.getTotalElements());
                response.put("totalPages", pageProjects.getTotalPages());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.OK);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns a project by id
     * @param projectId project id
     * @return HttpEntity
     */
    @GetMapping("/projects/{id}")
    public HttpEntity<?> getProject(@PathVariable(value = "id") Integer projectId) {
        Optional<Project> p = projectServiceImpl.findById(projectId);
        return projectServiceImpl.findById(projectId).isPresent() ? new ResponseEntity<>(p, HttpStatus.OK) : new ResponseEntity<>(new ModelNotFound("Project", "id", projectId), HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a project by id
     * @param projectId project id
     * @return ResponseEntity
     */
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId) {
        Optional<Project> projectToDelete = projectServiceImpl.findById(projectId);
        projectToDelete.ifPresent(projectServiceImpl::delete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
