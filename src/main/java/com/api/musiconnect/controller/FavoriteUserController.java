package com.api.musiconnect.controller;

import com.api.musiconnect.dto.response.FavoriteUserDTO;
import com.api.musiconnect.service.FavoriteUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favoritos/perfiles")
public class FavoriteUserController {

    private final FavoriteUserService favoriteUserService;

    public FavoriteUserController(FavoriteUserService favoriteUserService) {
        this.favoriteUserService = favoriteUserService;
    }

    @PostMapping
    public ResponseEntity<Void> marcarFavorito(@RequestParam Long userId, @RequestParam Long favoritoId) {
        favoriteUserService.marcarFavorito(userId, favoritoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dto")
    public ResponseEntity<List<FavoriteUserDTO>> obtenerFavoritosDTO(@RequestParam Long userId) {
        return ResponseEntity.ok(favoriteUserService.obtenerFavoritosDTO(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarFavorito(@RequestParam Long userId, @RequestParam Long favoritoId) {
        favoriteUserService.eliminarFavorito(userId, favoritoId);
        return ResponseEntity.noContent().build();
    }
}
