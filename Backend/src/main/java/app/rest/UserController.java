package app.rest;

import app.exceptions.FileIsNotRightExtension;
import app.models.User.User;
import app.exceptions.UserNotFoundException;
import app.repositories.JPAUserRepository;
import app.security.JWTokenInfo;
import app.server.Amazon.BucketNames;
import app.service.FileUtils.FileStore;
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
    public  ResponseEntity<Iterable<User>> getAllUsers(@RequestParam(value = "email",required = false) String email) {

        if(email != null) {
            System.out.println(email);
            List<User> users = userRepo.findByEmail(email);
            if (users.size() == 0) {
                throw new UserNotFoundException("User with email " + email + " not found");
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }



    @GetMapping("/users/{id}")
    public User getUserByEmail(
            @PathVariable Integer id) {
        User userById = userRepo.findById(id);
        if(userById == null) {
            throw new UserNotFoundException("id = " + id );
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
    @JsonView(UserView.Update.class)
    public ResponseEntity<Object> updateUser(@RequestBody User user) {

        System.out.println(user);

        User userById = userRepo.findById(user.getId());

        userById.setCountryKey(user.getCountryKey());

        if(userById == null) {
            throw new UserNotFoundException("id = " + user.getId());
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
            relpath= fileStore.upload(path, fileName, Optional.of(fileStore.prepareUplaud(file)), file.getInputStream(),true);
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload file", e);
        }

        User r = userRepo.uplaudProfilePictureForUser(relpath,id);
        return ResponseEntity.accepted().body(r);
    }
}