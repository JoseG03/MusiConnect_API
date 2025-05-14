package com.api.musiconnect.repository;

import com.api.musiconnect.model.entity.FavoriteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteUserRepository extends JpaRepository<FavoriteUser, Long> {
    List<FavoriteUser> findByUserId(Long userId);
    void deleteByUserIdAndFavoritoId(Long userId, Long favoritoId);
    boolean existsByUserIdAndFavoritoId(Long userId, Long favoritoId);
}
