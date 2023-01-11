package app.repositories;

import app.models.User.User;
import app.repositories.Interfaces.CustomCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * A JPA implementation of a user repository
 *
 * @author Pepijn Dik
 */
@Repository
@Transactional
public class JPAUserRepository implements CustomCrudRepository<User, Integer> {

    /**
     * The entity manager is used to perform crud actions on the database.
     */
    @Autowired
    private EntityManager em;

    /**
     * This method is used to save a user to the database.
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    @Override
    public User save(User user) {
        return em.merge(user);
    }

    /**
     * This method is used to find a user by its id.
     *
     * @param primaryKey The id of the entity to be found.
     * @return The user.
     */
    @Override
    public User findById(Integer primaryKey) {
        return em.find(User.class, primaryKey);
    }

    /**
     * This method is used to delete a user from the database
     *
     * @param user the user to be deleted.
     */
    @Override
    public void delete(User user) {

        User toRemove = em.merge(user);

        em.remove(toRemove);
    }

    /**
     * This method is used to find out if the user exists in the database.
     *
     * @param primaryKey The primary key of the entity to look for
     * @return true if the entity exists, false if it doesn't.
     */
    @Override
    public boolean existsById(Integer primaryKey) {
        return this.findById(primaryKey) != null;
    }

    /**
     * This method is used to find all users in the database with a certain email.
     *
     * @param email The email of the user to be found.
     * @return The user.
     */
    public List<User> findByEmail(String email) {
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
            .setParameter("email", email)
            .getResultList();
    }

    /**
     * This method is used to find all the users in the database.
     *
     * @return A list of all the users.
     */
    @Override
    public Iterable<User> findAll() {
        return em.createQuery("SELECT a FROM User a", User.class).getResultList();
    }

    /**
     * A method to check if a user exists by username
     *
     * @param username The username of the user to be found.
     * @return Optional<User>
     */
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(em.createQuery("SELECT u FROM User u WHERE u.name = :username", User.class)
            .setParameter("username", username)
            .getSingleResult());
    }

    /**
     * Upload a profile picture for the given user.
     * @param url The url of the profile picture.
     * @param id The id of the user.
     *
     * @return The updated user.
     */
    public User uploadProfilePictureForUser(String url, Integer id) {
        em.createQuery("UPDATE User u SET u.profileImage = :url WHERE u.id = :id")
            .setParameter("url", url)
            .setParameter("id", id)
            .executeUpdate();
        return this.findById(id);
    }

    /**
     * This method is used to count the amount of users in the database.
     *
     * @return The amount of users in the database.
     */
    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
    }

}
