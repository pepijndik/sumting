package nl.hva.backend.rest;

import nl.hva.backend.models.Project;
import nl.hva.backend.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProjectController {
    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects(){
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable Long projectId){
        return new ResponseEntity<>(projectService.getProjectRepository().findById(projectId), HttpStatus.OK);
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId){
        Project projectToDelete = projectService.getProjectRepository().findById(projectId);
        projectService.deleteProject(projectToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
