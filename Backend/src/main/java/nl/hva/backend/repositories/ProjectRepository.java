package nl.hva.backend.repositories;

import nl.hva.backend.models.Project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository implements CrudRepository<Project, Integer> {


    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends Project> S save(S entity) {
        return null;
    }

    @Override
    public Project findById(Integer id) {
        String sql = "SELECT * FROM " + Project.TABLE_NAME +" WHERE project_key = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Project>(Project.class), id);
    }

    @Override
    public Iterable<Project> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + Project.TABLE_NAME, new BeanPropertyRowMapper<Project>(Project.class));

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Project entity) {

    }

    @Override
    public boolean existsById(Integer primaryKey) {
        return false;
    }
}
