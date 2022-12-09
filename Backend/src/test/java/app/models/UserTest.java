package app.models;

import app.models.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


import static org.springframework.test.util.AssertionErrors.*;

public class UserTest {

    User user1, user2, user3;

    @BeforeEach
    void setup() {

        this.user1 = User.buildRandom("test");
        this.user2 = User.buildRandom("admin");
    }

    @Test
    void passwordIsHashedConsistentlyAndSecurely() {
        user1.setPassword("welcome");
        user2.setPassword("welcome");
        User user3 = new User(1001, user1.getName(), user1.getEmail());
        user3.setPassword("welcome");

        assertTrue("password hash is too short to be secure",
                user1.getHashedPassword().length() > 50);
        assertNotEquals(user1.getHashedPassword(), user2.getHashedPassword(),
                "different users with the same password should get different hashes");
        assertNotEquals("different instances of the same user should apply the same hash",
                user3.getHashedPassword(), user1.getHashedPassword());

        user3.setType(User.Type.ADMIN);
        user3.setPassword("welcome");
        assertEquals("change of role may not change the password hash", user1.getHashedPassword(), user1.getHashedPassword());

        user3.setPassword("welcome2");
        assertNotEquals(user1.getHashedPassword(), user3.getHashedPassword(),
                "different passwords should give different hashes");

        user1.setPassword(null);
        assertTrue("null password should still produce a secure hash",
                user3.getHashedPassword().length() > 50);
    }

    @Test
    void passwordCanBeVerified() {
        user1.setPassword("welcome1");
        user2.setPassword("welcome2");
        assertTrue("correct password should be verified", user1.verifyPassword("welcome1"));
        assertTrue("correct password should be verified", user2.verifyPassword("welcome2"));
        assertFalse("incorrect password should not be verified", user1.verifyPassword("welcome2"));
        assertFalse("null password should not be verified", user1.verifyPassword(null));
    }

    @Test()
    public void givenJsonOfArray_whenDeserializing_thenException()
            throws JsonProcessingException, IOException {
        String actualMessage = "";
        try {
            String json
                    = "[{\"id\":1,\"name\":\"John\"},{\"id\":2,\"name\":\"Adam\"}]";
            ObjectMapper mapper = new ObjectMapper();
            mapper.reader().forType(User.class).readValue(json);
        } catch (JsonMappingException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = "For input string";

        assertFalse(actualMessage, actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenJsonOfArray_whenDeserializing_thenCorrect()
            throws JsonProcessingException, IOException {

        String json
                = "[{\"id\":1,\"name\":\"John\"},{\"id\":2,\"name\":\"Adam\"}]";

        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.reader().forType(new TypeReference<List<User>>() {}).readValue(json);
        assertEquals("",2, users.size());
    }
}
