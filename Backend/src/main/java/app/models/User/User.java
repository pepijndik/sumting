package app.models.User;

import app.models.Country;
import app.models.Identifiable;
import app.views.OrderView;
import app.views.UserView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import app.security.PasswordEncoder;

/**
 * A representation of a user
 * <p>
 * Author: Pepijn dik
 */
@Entity
@Table(name = User.TABLE_NAME)
public class User implements Identifiable<Integer> {

    public User() {

    }

    public User(Integer id, String name, String email) {
        setId(id);
        setName(name);
        setEmail(email);
        setType(Type.PERSON);
        setCountryKey(25);
        long minDay = LocalDate.of(2022, 8, 1).toEpochDay();
        long maxDay = LocalDate.of(2022, 11, 29).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate sellDate = LocalDate.ofEpochDay(randomDay);
    }


    public static enum Type {
        BUSINESS,
        PERSON,
        ADMIN
    }

    public static final String TABLE_NAME = "\"User\"";
    @Id
    @JsonView({UserView.User.class, OrderView.Order.class, UserView.Update.class})
    @Column(name = "user_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id_ext", nullable = true, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer user_key_ext;

    @JsonView({UserView.User.class, OrderView.Order.class, UserView.Update.class})
    @Column(name = "user_name", nullable = false)
    private String name;

    @JsonView({UserView.User.class, OrderView.Order.class, UserView.Update.class})
    @Column(name = "email", nullable = false)
    private String email;

    @JsonView(UserView.User.class)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime udatedAt;
    @JsonIgnore
    @Column(name = "user_password", nullable = true, columnDefinition = "varchar(255)")
    private String encodedPassword;


    @JsonIgnore
    @Column(name = "user_secret_code", nullable = true)
    private String secretCode;

    @JsonIgnore
    @Column(name = "user_twofactor_enabled", nullable = true, columnDefinition = "boolean")
    private Boolean twoFactorEnabled;


    @JsonView({UserView.User.class, OrderView.Order.class, UserView.Update.class})
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, columnDefinition = "varchar(255) default 'PERSON'")
    private User.Type user_type;

    @Transient
    private Integer country_key;

    @JsonView({UserView.User.class, OrderView.Order.class, UserView.Update.class})
    @JoinColumn(name = "country_key", referencedColumnName = "country_key", insertable = true, updatable = true)
    @OneToOne(cascade = CascadeType.DETACH)
    private Country country;

    @JsonView({UserView.User.class, UserView.Update.class, UserView.Login.class})
    @Column(name = "profile_image", nullable = true)
    private String profileImage;

    @JsonView({UserView.User.class, UserView.Update.class, UserView.Login.class})
    @Column(name = "profile_text", nullable = true)
    private String profileText;

    @JsonView({UserView.User.class})
    @Column(name = "user_stripe_id", nullable = true)
    private String stripeId;


    @JsonView(UserView.User.class)
    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled != null && twoFactorEnabled;
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

    public void setCountryKey(Integer country) {
        this.country_key = country;
    }

    public Integer getCountryKey() {
        return country_key;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public boolean validateEncodedPassword(String given) {
        return encodedPassword.equals(given);
    }

    public String hashPassword(String password) {
        return PasswordEncoder.encode("id_" + this.id + password);
    }

    public boolean verifyPassword(String password) {
        return password != null && this.hashPassword(password).equals(this.encodedPassword);
    }

    public void setPassword(String password) {
        this.encodedPassword = this.hashPassword(password);
    }

    @JsonIgnore()
    public String getHashedPassword() {
        return encodedPassword;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    public void setUser_key_ext(Integer user_key_ext) {
        this.user_key_ext = user_key_ext;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("{ email=%s, callName=%s, id=%d, country=%s}", this.email, this.name, this.id, this.country);
    }

    public static User buildRandom(String uname) {
        System.out.printf("Building random user with name %s", uname);
        User user = buildRandom();
        byte[] nameLenght = new byte[7]; // length is bounded by 7
        new Random().nextBytes(nameLenght);
        user.setName(uname);
        user.setEmail(uname + "@hva.nl");
        return user;
    }

    public static User buildRandom() {
        User user = new User();
        byte[] nameLenght = new byte[7]; // length is bounded by 7
        new Random().nextBytes(nameLenght);
        String name = "";
        name = new String(nameLenght, java.nio.charset.StandardCharsets.UTF_8) + ThreadLocalRandom.current().nextInt(1, 1000);
        user.setName(name);
        user.setEmail(name.toLowerCase().replace(" ", ".") + "@hva.nl");
        user.setType(Type.PERSON);
        int c = new Random().nextInt(1, 3);
        user.setCountryKey(c);
        long minDay = LocalDate.of(2022, 8, 1).toEpochDay();
        long maxDay = LocalDate.of(2022, 11, 29).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate created_at = LocalDate.ofEpochDay(randomDay);
        user.setCreatedAt(created_at.atStartOfDay());
        System.out.printf("User: %s", user);
        return user;
    }

    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }
}
