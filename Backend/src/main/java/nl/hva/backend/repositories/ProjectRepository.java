package nl.hva.backend.repositories;

import nl.hva.backend.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository implements EntityRepository<Project> {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project save(Project entity) {
        return null;
    }


    public Project findById(Integer id) {
        return null;
    }

    @Override
    public Project update(Project entity) {
        return null;
    }

    @Override
    public void delete(Project entity) {

    }

    /**
     * Select all projects from the database
     *
     * @return List of all projects
     */
    @Override
    public List<Project> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + Project.TABLE_NAME, new BeanPropertyRowMapper<Project>(Project.class));
    }
}
