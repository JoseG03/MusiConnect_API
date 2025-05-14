package com.api.musiconnect.service;

import com.api.musiconnect.dto.response.FavoriteUserDTO;
import com.api.musiconnect.mapper.FavoriteUserMapper;
import com.api.musiconnect.model.entity.FavoriteUser;
import com.api.musiconnect.model.entity.User;
import com.api.musiconnect.repository.FavoriteUserRepository;
import com.api.musiconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteUserService {

    private final FavoriteUserRepository favoriteUserRepo;
    private final UserRepository userRepo;

    public FavoriteUserService(FavoriteUserRepository favoriteUserRepo, UserRepository userRepo) {
        this.favoriteUserRepo = favoriteUserRepo;
        this.userRepo = userRepo;
    }

    public void marcarFavorito(Long userId, Long favoritoId) {
        if (!favoriteUserRepo.existsByUserIdAndFavoritoId(userId, favoritoId)) {
            FavoriteUser fav = new FavoriteUser();
            fav.setUser(userRepo.findById(userId).orElseThrow());
            fav.setFavorito(userRepo.findById(favoritoId).orElseThrow());
            favoriteUserRepo.save(fav);
        }
    }

    public List<FavoriteUserDTO> obtenerFavoritosDTO(Long userId) {
        return favoriteUserRepo.findByUserId(userId).stream()
                .map(FavoriteUserMapper::toDTO)
                .toList();
    }

    public void eliminarFavorito(Long userId, Long favoritoId) {
        favoriteUserRepo.deleteByUserIdAndFavoritoId(userId, favoritoId);
    }
}

