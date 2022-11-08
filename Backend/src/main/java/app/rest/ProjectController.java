package app.rest;

import app.exceptions.ModelNotFound;
import app.models.Project.Project;
import app.repositories.Project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import app.SumtingBackend;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @GetMapping("/projects")
    public ResponseEntity<Iterable<Project>> getAllProjects(
            @RequestParam(value = "pageNo", defaultValue = SumtingBackend.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = SumtingBackend.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = SumtingBackend.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = SumtingBackend.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
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
