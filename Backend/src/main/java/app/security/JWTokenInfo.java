package app.security;

import app.enums.UserRole;
import app.models.User.User;

import java.util.Date;

/**
 * Class with useful information of token
 *
 * Author: Pepijn dik
 */

public class JWTokenInfo {

    public static final String KEY = "token";

    private Integer id;
    private boolean admin;

    private User user;
    private Date issuedAt;
    private Date expiration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }


    @Override
    public String toString() {
        return "JWTokenInfo{" +
                "id = " + this.getId() +
                ", User = " + this.getUser() +
                ", issuedAt=" + issuedAt +
                ", expiration=" + expiration +
                '}';
    }

    public boolean twoFactorEnabled() {
        return this.getUser().isTwoFactorEnabled();
    }
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
