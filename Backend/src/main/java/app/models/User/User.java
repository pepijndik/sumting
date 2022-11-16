package app.models.User;

import app.models.Country;
import app.models.Identifiable;
import app.views.UserView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;

import app.exceptions.TwofactorGenerationException;
import dev.samstevens.totp.secret.DefaultSecretGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A representation of a user
 *
 * Author: Pepijn dik
 */
@Entity
@Table(name = User.TABLE_NAME)
public class User implements Identifiable<Integer> {

    public static enum Type {
        BUSINESS,
        PERSON,
        ADMIN
    }
    public static final String TABLE_NAME ="\"User\"";
    @Id
    @JsonView(UserView.User.class)
    @Column(name = "user_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id_ext", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer user_key_ext;

    @JsonView(UserView.User.class)
    @Column(name = "user_name",nullable = false)
    private String name;

    @JsonView(UserView.User.class)
    @Column(name = "email",nullable = false)
    private String email;

    @JsonView(UserView.User.class)
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = true)
    private LocalDateTime udatedAt;
    @JsonIgnore
    @Column(name = "user_password", nullable = true, columnDefinition = "varchar(255)")
    private String encodedPassword;


    @JsonIgnore
    @Column(name = "user_secret_code",nullable = true)
    private String secretCode;

    @JsonView(UserView.Login.class)
    @Column(name = "user_twofactor_enabled",nullable = true, columnDefinition = "boolean default false")
    private Boolean TwoFactorEnabled;


    @JsonView(UserView.User.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type",nullable = false,columnDefinition = "varchar(255) default 'PERSON'")
    private User.Type user_type;

    @Column(name = "country_key",nullable = false)
    private Integer country_key;

    @JsonView(UserView.User.class)
    @JoinColumn(name = "country_key", referencedColumnName = "country_key", insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Country country;

    @JsonIgnore
    public boolean isTwoFactorEnabled() {
        return TwoFactorEnabled != null && TwoFactorEnabled;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setType(User.Type user_type) {
        this.user_type = user_type;
    }
    public Type getUser_type() {
        return user_type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Integer country) {
        this.country_key = country;
    }


    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public boolean validateEncodedPassword(String given) {
        return encodedPassword.equals(given);
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id =id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }
    @JsonIgnore
    public String getSecret() {
        return this.secretCode;
    }
    public void setSecret(String secret) {
        this.secretCode = secret;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }


}
