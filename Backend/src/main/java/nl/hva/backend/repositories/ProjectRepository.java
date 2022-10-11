package nl.hva.backend.repositories;

import nl.hva.backend.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository implements CrudRepository<Project, Long> {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends Project> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Project> findById(Long primaryKey) {
        return Optional.ofNullable((Project) jdbcTemplate.query("SELECT * FROM " + Project.TABLE_NAME +" WHERE project_key = ?", new BeanPropertyRowMapper<Project>(Project.class)));
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
    public boolean existsById(Long primaryKey) {
        return false;
    }
}
