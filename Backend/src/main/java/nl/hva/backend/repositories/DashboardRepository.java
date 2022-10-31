package nl.hva.backend.repositories;

import nl.hva.backend.models.Dashboard.Graph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends CrudRepository<Graph, Integer>{

    List<Graph> findAll();
    Graph findByMonth(int orderDate);
}
