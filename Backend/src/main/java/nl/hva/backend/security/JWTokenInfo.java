package nl.hva.backend.security;

import java.util.Date;

/**
 * Class with useful information of token
 *
 * Author: MFK
 */
public class JWTokenInfo {

    public static final String KEY = "tokenInfo";

    private String email;
    private boolean admin;
    private Date issuedAt;
    private Date expiration;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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
                "email='" + email + '\'' +
                ", admin=" + admin +
                ", issuedAt=" + issuedAt +
                ", expiration=" + expiration +
                '}';
    }
}
