package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blacklist_token")
public class BlacklistToken {
    @Id
    @Column(name = "token")
    private String token;
    @Column(name = "active")
    private boolean active;

    public BlacklistToken(String token, boolean active) {
        this.token = token;
        this.active = active;
    }

    public BlacklistToken() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
