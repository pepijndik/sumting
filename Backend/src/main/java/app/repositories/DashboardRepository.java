package app.repositories;

import app.models.Project.Project;
import app.repositories.Interfaces.CrudRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import app.models.Dashboard.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Date;

@Repository
@Transactional
public class DashboardRepository implements CrudRepository<Graph, Integer> {

    @Autowired
    private EntityManager em;


    @Override
    public Graph save(Graph entity) {
        return null;
    }

    @Override
    public Graph findById(Integer primaryKey) {
        return null;
    }

    @Override
    public Iterable<Graph> findAll() {
        return em.createQuery("SELECT a FROM Graph a", Graph.class).getResultList();
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public Iterable<Graph> findByMonth(Date pastMonths){
        return em.createQuery("SELECT a FROM Graph a WHERE a.createdAt<=current_date AND a.createdAt >= :pastMonths",
                Graph.class).setParameter("pastMonths", pastMonths).getResultList();
    }

    public Iterable<String> findAllDescriptions(){
        return em.createQuery("SELECT p.description_long FROM Project p", String.class).getResultList();
    }

    public Iterable<Project> findAllByDescription(String description){
        return em.createQuery("SELECT a FROM Project a WHERE a.description_long = :description",
                Project.class).setParameter("description", description).getResultList();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Graph entity) {

    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return false;
    }
}
