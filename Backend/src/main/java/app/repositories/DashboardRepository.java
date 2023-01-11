package app.repositories;

import app.repositories.Interfaces.CustomCrudRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import app.models.Dashboard.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Date;

/**
 * This repository is used to perform crud actions on the graph table in the database.
 */
@Repository
@Transactional
public class DashboardRepository implements CustomCrudRepository<Graph, Integer> {

    /**
     * The entity manager is used to perform crud actions on the database.
     */
    @Autowired
    private EntityManager em;

    /**
     * This method is used to save a graph to the database.
     *
     * @param entity The graph to be saved.
     * @return The saved graph.
     */
    @Override
    public Graph save(Graph entity) {
        return null;
    }

    /**
     * This method is used to find a graph by its id.
     *
     * @param primaryKey The id of the entity to be found.
     * @return The graph.
     */
    @Override
    public Graph findById(Integer primaryKey) {
        return null;
    }

    /**
     * This method is used to find all graphs in the database.
     *
     * @return A list of all the graphs.
     */
    @Override
    public Iterable<Graph> findAll() {
        return em.createQuery("SELECT a FROM Graph a", Graph.class).getResultList();
    }

    /**
     * This method is used to count the amount of graphs in the database.
     *
     * @return The amount of graphs in the database.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Iterable<Graph> findByMonth(Date pastMonths) {
        return em.createQuery("SELECT a FROM Graph a WHERE a.createdAt<=current_date AND a.createdAt >= :pastMonths",
            Graph.class).setParameter("pastMonths", pastMonths).getResultList();
    }

    /**
     * This method is used to count the amount of graphs in the database.
     *
     * @return The amount of graphs in the database.
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * This method is used to delete a graph from the database.
     *
     * @param entity The graph to be deleted.
     */
    @Override
    public void delete(Graph entity) {

    }

    /**
     * This method is used to check if a graph exists in the database.
     *
     * @param primaryKey The primary key of the entity to look for
     * @return True if the entity exists, false if it doesn't.
     */
    @Override
    public boolean existsById(Integer primaryKey) {
        return false;
    }
}
