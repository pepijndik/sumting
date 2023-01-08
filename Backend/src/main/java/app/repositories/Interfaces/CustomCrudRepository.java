package app.repositories.Interfaces;


import app.models.Order.OrderLine;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface CustomCrudRepository<T, ID> extends Repository<T, ID> {

   T save(T entity);

    T findById(ID primaryKey);

    Iterable<T> findAll();

    long count();

    void delete(T entity);

    boolean existsById(ID primaryKey);

}