package app.rest;

import app.models.User.User;
import app.exceptions.UserNotFoundException;
import app.exceptions.AuthorizationException;
import app.repositories.JPAUserRepository;
import app.security.JWTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private JPAUserRepository userRepo;

    @GetMapping("/users")
    public  ResponseEntity<Iterable<User>> getAllUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
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