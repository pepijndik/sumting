package nl.hva.backend.repositories;


import nl.hva.backend.models.User.User;

import java.util.List;

/**
 * A definition of a user repository
 *
 * Author: MFK
 */
public interface UserRepository {
    User save(User user);

    void delete(User user);

    List<User> findAll();

    User findByEmail(String email);
}
