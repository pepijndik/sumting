package nl.hva.backend.repositories;

import nl.hva.backend.models.Identifiable;

import java.util.List;

public interface  EntityRepository <E extends Identifiable>
{
    E save(E entity);
    E findById(Long id);
    E update(E entity);
    void delete(E entity);
    List<E> findAll();
}
