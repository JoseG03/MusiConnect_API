package com.api.musiconnect.mapper;

import com.api.musiconnect.dto.response.FavoriteUserDTO;
import com.api.musiconnect.model.entity.FavoriteUser;

public class FavoriteUserMapper {
    public static FavoriteUserDTO toDTO(FavoriteUser entity) {
        FavoriteUserDTO dto = new FavoriteUserDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setFavoritoId(entity.getFavorito().getId());
        dto.setFavoritoUsername(entity.getFavorito().getUsername());
        return dto;
    }
}