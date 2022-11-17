<<<<<<<< HEAD:Backend/src/main/java/app/repositories/CrudRepository.java
package app.repositories;
========
package app.repositories.interfaces;
>>>>>>>> origin/main:Backend/src/main/java/app/repositories/interfaces/CrudRepository.java

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface CrudRepository<T, ID> extends Repository<T, ID> {

   T save(T entity);

    T findById(ID primaryKey);

    Iterable<T> findAll();

    long count();

    void delete(T entity);

    boolean existsById(ID primaryKey);

}