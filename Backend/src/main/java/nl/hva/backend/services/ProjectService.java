package nl.hva.backend.services;


import nl.hva.backend.models.Project;
import nl.hva.backend.repositories.EntityRepository;
import nl.hva.backend.repositories.ProjectRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProjectService {

        private final ProjectRepository projectRepository;


        public ProjectService(ProjectRepository projectRepository) {
            this.projectRepository = projectRepository;
        }

        public void saveProject(Project project){
            getProjectRepository().save(project);
        }

        public List<Project> getAllProjects(){
            return getProjectRepository().findAll();
        }

        public void deleteProject(Project post){
            getProjectRepository().delete(post);
        }

        public ProjectRepository getProjectRepository() {
            return projectRepository;
        }



}
