package app.rest;

import app.exceptions.FileIsNotRightExtension;
import app.models.User.User;
import app.exceptions.UserNotFoundException;
import app.repositories.JPAUserRepository;
import app.security.JWTokenInfo;
import app.server.Amazon.AmazonConfig;
import app.server.Amazon.BucketNames;
import app.service.FileStore;
import app.views.UserView;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private JPAUserRepository userRepo;
    @Autowired
    private FileStore fileStore;
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


    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) {

//        if(!tokenInfo.getUser()) {
//            throw new AuthorizationException("only administrators can remove members");
//        }

        User user = userRepo.findById(id);
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

    @PostMapping("/users/{id}/profile-picture")
    @JsonView(UserView.User.class)
    public ResponseEntity<Object> uplaudProfile(@PathVariable Integer id,@RequestParam("file")MultipartFile file) throws FileUploadException {

        if(!fileStore.isImage(file)) {
            //check if the file is an image
            throw new FileIsNotRightExtension("File must be an image");
        }

        String path = String.format("%s/%s/%d", BucketNames.BASE.getBucketName(), "users",id);
        String fileName = String.format("%s", file.getOriginalFilename());
        String relpath= "";
        try{
            //Path format /{bucket-name}/{id}/{file-name}
            relpath= fileStore.upload(path, fileName, Optional.of(fileStore.prepareUplaud(file)), file.getInputStream());
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload file", e);
        }

        User r = userRepo.uplaudProfilePictureForUser(relpath,id);
        return ResponseEntity.accepted().body(r);
    }
}