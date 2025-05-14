package com.api.musiconnect.dto.response;

public class FavoriteUserDTO {
    private Long id;
    private Long userId;
    private Long favoritoId;
    private String favoritoUsername;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFavoritoId() {
        return favoritoId;
    }

    public void setFavoritoId(Long favoritoId) {
        this.favoritoId = favoritoId;
    }

    public String getFavoritoUsername() {
        return favoritoUsername;
    }

    public void setFavoritoUsername(String favoritoUsername) {
        this.favoritoUsername = favoritoUsername;
    }

}
