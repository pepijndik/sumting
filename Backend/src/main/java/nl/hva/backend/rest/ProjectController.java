package nl.hva.backend.rest;

import nl.hva.backend.exceptions.ModelNotFound;
import nl.hva.backend.services.models.Project.Project;
import nl.hva.backend.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectController {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @GetMapping("/projects")
    public ResponseEntity<Iterable<Project>> getAllProjects() {
        return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public HttpEntity<?> getProject(@PathVariable(value = "id") Integer projectId) {
        Project p = projectRepository.findById(projectId);
        return projectRepository.findById(projectId) != null ? new ResponseEntity<>(p, HttpStatus.OK) : new ResponseEntity<ModelNotFound>(new ModelNotFound("Project", "id", projectId),HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId) {
        Project projectToDelete = projectRepository.findById(projectId);
        projectRepository.delete(projectToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
