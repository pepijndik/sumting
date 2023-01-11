package app.repositories;

import app.models.Order.OrderLine;
import app.models.Project.Project;
import com.fasterxml.jackson.annotation.JsonFormat;
import app.models.Dashboard.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Optional;

@Repository
@Transactional
public class DashboardRepository implements CrudRepository<Graph, Integer> {

    @Autowired
    private EntityManager em;


    @Override
    public Graph save(Graph entity) {
        return em.merge(entity);
    }

    @Override
    public <S extends Graph> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Graph> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Iterable<Graph> findAll() {
        return em.createQuery("SELECT a FROM Graph a", Graph.class).getResultList();
    }

    @Override
    public Iterable<Graph> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public Iterable<Graph> findByMonth(Date pastMonths){
        return em.createQuery("SELECT a FROM Graph a WHERE a.createdAt<=current_date AND a.createdAt >= :pastMonths",
                Graph.class).setParameter("pastMonths", pastMonths).getResultList();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }


    @Override
    public void delete(Graph entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Graph> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return false;
    }
}
