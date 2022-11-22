package app.repositories;

import app.models.User.User;
import app.repositories.interfaces.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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


    public List<User> findByEmail(String email) {
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public Iterable<User> findAll() {
        return em.createQuery("SELECT a FROM User a", User.class).getResultList();
    }

    /**
     * A method to check if a user exists by username
     * @param username
     * @return Optional<User>
     */
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(em.createQuery("SELECT u FROM User u WHERE u.name = :username", User.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    public User uplaudProfilePictureForUser(String url, Integer  id) {
        em.createQuery("UPDATE User u SET u.profileImage = :url WHERE u.id = :id")
                .setParameter("url", url)
                .setParameter("id", id)
                .executeUpdate();
        return this.findById(id);
    }

    @Override
    public long count() {
        return 0;
    }

}
