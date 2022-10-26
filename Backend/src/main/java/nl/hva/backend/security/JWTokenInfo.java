package nl.hva.backend.security;

import nl.hva.backend.Enums.UserRole;

import java.util.Date;

/**
 * Class with useful information of token
 *
 * Author: MFK
 */

public class JWTokenInfo {

    public static final String KEY = "tokenInfo";

    private String email;
    private UserRole Role;
    private Date issuedAt;
    private Date expiration;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return this.Role;
    }

    public boolean isAdmin(){
        return this.Role == UserRole.ADMIN;
    }

    public void setAdmin(boolean admin) {
        if(admin){
            this.setRole(UserRole.ADMIN);
        }
        else{
            this.setRole(UserRole.USER);
        }
    }

    public void setUser(boolean user) {
        if(user){
          this.setRole(UserRole.USER);
        }

    }
    public void setRole(UserRole role){
        this.Role = Role;
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
                ", Role=" + this.getRole() +
                ", issuedAt=" + issuedAt +
                ", expiration=" + expiration +
                '}';
    }
}
