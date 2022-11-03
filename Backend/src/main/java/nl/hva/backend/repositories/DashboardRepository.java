package nl.hva.backend.repositories;

import com.fasterxml.jackson.annotation.JsonFormat;
import nl.hva.backend.models.Dashboard.Graph;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;

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
    public Iterable<Graph> findByMonth(@Param("pastMonths") LocalDate pastMonths){
        return em.createQuery("SELECT a FROM Graph a WHERE a.createdAt<=current_date AND a.createdAt >= '2022-10-03'",
                Graph.class).getResultList();
//        return em.createQuery("SELECT a FROM Graph a WHERE a.createdAt<=current_date AND a.createdAt >= :pastMonths",
//                Graph.class).setParameter("pastMonths", pastMonths).getResultList();
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
