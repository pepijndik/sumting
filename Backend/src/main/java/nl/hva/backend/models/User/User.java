package nl.hva.backend.models.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A representation of a user
 *
 * Author: MFK
 */
@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public static final String TABLE_NAME = "user";
    @Id
    @Column(name = "user_key")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column(name = "user_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime udatedAt;
    @JsonIgnore
    @Column(name = "user_password", nullable = true)
    private String encodedPassword;


    @Column(name = "user_secret_code",nullable = true)
    private String secretCode;

    @Column(name = "user_twofactor_enabled",nullable = true)
    private Boolean TwoFactorEnabled;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public boolean validateEncodedPassword(String given) {
        return encodedPassword.equals(given);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }


}
