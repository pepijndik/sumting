package app.repositories;

import app.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * A JPA implementation of a user repository
 *
 * Author: MFK
 */
@Repository
@Transactional
public class JPAUserRepository implements CrudRepository<User, Integer > {

    @Autowired
    private EntityManager em;

    @Override
    public User save(User user) {
        return em.merge(user);
    }

    @Override
    public User findById(Integer  primaryKey) {
        return em.find(User.class, primaryKey);
    }

    @Override
    public void delete(User user) {

        User toRemove = em.merge(user);

        em.remove(toRemove);
    }

    @Override
    public boolean existsById(Integer  primaryKey) {
        return this.findById(primaryKey) != null;
    }


    public User findByEmail(String email) {
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public Iterable<User> findAll() {
        return em.createQuery("SELECT a FROM User a", User.class).getResultList();
    }

    @Override
    public long count() {
        return 0;
    }

}
