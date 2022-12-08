package app.rest;


import app.models.User.User;
import app.repositories.DataLoader;
import app.repositories.JPAUserRepository;
import app.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(DataLoader.class)
public class AuthenticationControllerTests {

    @Autowired
    CommandLineRunner dataLoader;

    @Autowired
    private TestRestTemplate restTemplate;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    @Autowired // injects an implementation of BooksRepository here.
    private JPAUserRepository userRepo;

    private List<User> userList;
    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        this.userList = (List<User>) this.userRepo.findAll();
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }
    private class SignOnInfo {
        public String email;
        public String password;

        public SignOnInfo(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Test
    public void FindAllReturnsAll() {
        // check Users have been loaded
        assertTrue(this.userList.size() > 0);
    }

    @Test
    public void FindByIdReturnsProperUser() {
        // check all books can be found by id
        for (int i = 0; i < this.userList.size(); i++) {
            User user = this.userRepo.findById(this.userList.get(i).getId());
            assertEquals(this.userList.get(i).getName(), user.getName());
            assertEquals(this.userList.get(i).getEmail(), user.getEmail());
        }
        // check non-existing book
        User user = this.userRepo.findById(9999999);
        assertNull(user);
    }


//    @Test
//    public void postLoginSucceeds() {
//
//        // post a new Login request
//        SignOnInfo signOnInfo = new SignOnInfo("admin@hva.nl", "Test123!");
//        ResponseEntity<LoginResponse> response = null;
//        try{
//        response =  this.restTemplate.postForEntity("/auth", signOnInfo, LoginResponse.class);
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        assert response != null;
//        // check status code, location header and response body of post request
//        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode(),"The response status code should be 202, but was " + response.getStatusCode());
//
//        LoginResponse responseBody = response.getBody();
//        User signInUser = responseBody.getMe();
//        assert signInUser != null;
//        assertThat(signInUser.getId(),not(nullValue()));
//        assertThat(signInUser.getId(), is(greaterThan(0)));
//        assertEquals("admin", signInUser.getName());
//        String token = response.getHeaders().get("Authorization").get(0);
//        System.out.println(token);
//        assertThat(token, startsWith("Bearer "));
//    }


}
