package nl.hva.backend.rest;

import nl.hva.backend.models.User.User;
import nl.hva.backend.exceptions.UserNotFoundException;
import nl.hva.backend.exceptions.AuthorizationException;
import nl.hva.backend.repositories.JPAUserRepository;
import nl.hva.backend.security.JWTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private JPAUserRepository userRepo;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/users/{email}")
    public User getUserByEmail(
            @PathVariable String email) {

        User userById = userRepo.findByEmail(email);

        if(userById == null) {
            throw new UserNotFoundException("id = " + email );
        }

        return userById;
    }


    @DeleteMapping("/users/{email}")
    public ResponseEntity<User> deleteUser(@PathVariable String email, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) {

        if(!tokenInfo.isAdmin()) {
            throw new AuthorizationException("only administrators can remove members");
        }

        User user = getUserByEmail(email);

        userRepo.delete(user);

        return ResponseEntity.ok(user);

    }

    @PutMapping("/users")
    public ResponseEntity<Object> updateUser(@RequestBody User user) {

        User userById = userRepo.findByEmail(user.getEmail());

        if(userById == null) {
            throw new UserNotFoundException("id = " + user.getEmail());
        }

        userRepo.save(user);

        return ResponseEntity.ok().build();
    }
}