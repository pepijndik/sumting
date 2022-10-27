package nl.hva.backend.models.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import nl.hva.backend.exceptions.TwofactorGenerationException;
import nl.hva.backend.models.Country;
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
public class User {

    public static enum Type {
        PERSON,
        ADMIN
    }
    public static final String TABLE_NAME = "user";
    @Id
    @Column(name = "user_key")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  id;
    @Column(name = "user_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime udatedAt;
    @JsonIgnore
    @Column(name = "user_password", nullable = true, columnDefinition = "varchar(255)")
    private String encodedPassword;


    @Column(name = "user_secret_code",nullable = true)
    private String secretCode;

    @Column(name = "user_twofactor_enabled",nullable = true)
    private Boolean TwoFactorEnabled;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

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
