package nl.hva.backend.rest;

import com.fasterxml.jackson.databind.JsonMappingException;
import nl.hva.backend.models.Project;
import nl.hva.backend.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProjectController {

    private ProjectRepository projectRepository;

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable Integer projectId) {
        Project p = projectRepository.findById(projectId);
        if (p != null) {
            return new ResponseEntity<Project>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId) {
        Project projectToDelete = projectRepository.findById(projectId);
        projectRepository.delete(projectToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
