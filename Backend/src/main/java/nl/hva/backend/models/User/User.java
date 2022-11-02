package nl.hva.backend.models.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;

import nl.hva.backend.exceptions.TwofactorGenerationException;
import nl.hva.backend.models.*;
import nl.hva.backend.services.TwoFactorService;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
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
    @Column(name = "user_key", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id_ext", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer user_key_ext;
    @Column(name = "user_name",nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
    private String email;

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

    @Column(name = "user_twofactor_enabled",nullable = true)
    private Boolean TwoFactorEnabled;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type",nullable = false,columnDefinition = "varchar(255) default 'PERSON'")
    private User.Type user_type;

    @Column(name = "country_key",nullable = false)
    private Integer country_key;

    @JoinColumn(name = "country_key", referencedColumnName = "country_key", insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Country country;

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
    public void generateSecretCode() {
        this.secretCode = TwoFactorService.generateSecretKey();
    }

    /**
     * Generate a Qr to scan with a 2FA app
     */
    public void generateQrCode() throws TwofactorGenerationException
    {
        try{
            QrData qrData = TwoFactorService.generateQRData(this.getSecret(),this);
            TwoFactorService.generateQRUrl(qrData);
        }
        catch (QrGenerationException generationException){
          throw new TwofactorGenerationException("Failed to generate QR code");
        }
    }
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }


}
