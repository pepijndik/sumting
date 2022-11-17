<<<<<<<< HEAD:Backend/src/main/java/app/repositories/PagingAndSortingRepository.java
package app.repositories;
========
package app.repositories.interfaces;
>>>>>>>> origin/main:Backend/src/main/java/app/repositories/interfaces/PagingAndSortingRepository.java

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);
}