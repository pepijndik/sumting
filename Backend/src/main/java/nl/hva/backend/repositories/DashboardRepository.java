package nl.hva.backend.repositories;

import nl.hva.backend.models.Dashboard.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;

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

    public Iterable<Long> findByMonth(LocalDate pastMonths){
//        return em.createQuery("SELECT COUNT (a) FROM Graph a WHERE a.createdAt<=current_date AND a.createdAt >= '2022-09-01'",
//                Long.class).getResultList();
        return em.createQuery("SELECT COUNT(a) FROM Graph a WHERE a.createdAt<=current_date AND a.createdAt >= :pastMonths",
                Long.class).setParameter("pastMonths", pastMonths).getResultList();
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
