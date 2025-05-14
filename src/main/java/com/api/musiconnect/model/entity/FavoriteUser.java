package com.api.musiconnect.model.entity;

import jakarta.persistence.*;

@Entity
public class FavoriteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "favorito_id")
    private User favorito;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFavorito() {
        return favorito;
    }

    public void setFavorito(User favorito) {
        this.favorito = favorito;
    }
}

