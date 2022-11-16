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

    public static final String KEY = "tokenInfo";

    private UserRole Role;
    private User user;

    private int id;
    private Date issuedAt;
    private Date expiration;
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public UserRole getRole() {
        return this.Role;
    }

    public boolean isAdmin(){
        return this.Role == UserRole.ADMIN;
    }

    public boolean twoFactorEnabled(){
        return this.user.isTwoFactorEnabled();
    }

    public void setAdmin(boolean admin) {
        if(admin){
            this.setRole(UserRole.ADMIN);
        }
        else{
            this.setRole(UserRole.USER);
        }
    }
    public User getUser(){
        return this.user;
    }
    public void setUserRole(boolean user) {
        if(user){
          this.setRole(UserRole.USER);
        }

    }
    public void setRole(UserRole role){
        this.Role = role;
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
                ", Role=" + this.getRole() +
                ", issuedAt=" + issuedAt +
                ", expiration=" + expiration +
                '}';
    }
}
