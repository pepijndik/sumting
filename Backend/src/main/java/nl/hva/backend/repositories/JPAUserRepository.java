package nl.hva.backend.repositories;

import nl.hva.backend.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

/**
 * A JPA implementation of a user repository
 *
 * Author: MFK
 */
@Repository
@Transactional
public class JPAUserRepository implements CrudRepository<User, Long > {

    @Autowired
    private EntityManager em;

    @Override
    public User save(User user) {
        return em.merge(user);
    }

    @Override
    public User findById(Long  primaryKey) {
        return em.find(User.class, primaryKey);
    }

    @Override
    public void delete(User user) {

        User toRemove = em.merge(user);

        em.remove(toRemove);
    }

    @Override
    public boolean existsById(Long  primaryKey) {
        return this.findById(primaryKey) != null;
    }


    public User findByEmail(String email) {

        return em.find(User.class,email);
    }

    @Override
    public List<User> findAll() {

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u",User.class);

        return query.getResultList();
    }

    @Override
    public long count() {
        return 0;
    }

}
