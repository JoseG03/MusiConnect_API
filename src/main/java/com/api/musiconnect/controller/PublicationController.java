package com.api.musiconnect.controller;

import com.api.musiconnect.dto.request.*;
import com.api.musiconnect.service.CommentService;
import com.api.musiconnect.service.PublicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private CommentService commentService;

    // Crear publicación
    @PostMapping
    public ResponseEntity<Map<String, Object>> createPublication(@Valid @RequestBody CreatePublicationRequest request) {
        Long postId = publicationService.createPublication(request.getContent(), request.getUserId());
        Map<String, Object> response = new HashMap<>();
        response.put("postId", postId);
        response.put("message", "Publicación creada exitosamente.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Editar publicación
    @PutMapping("/{id}")
    public ResponseEntity<String> editPublication(@PathVariable Long id,
                                                  @Valid @RequestBody EditPublicationRequest request) {
        publicationService.editPublication(id, request.userId(), request.content());
        return ResponseEntity.ok("Publicación actualizada correctamente.");
    }

    // Comentar publicación
    @PostMapping("/{id}/comments")
    public ResponseEntity<String> commentPublication(@PathVariable Long id,
                                                     @Valid @RequestBody CreateCommentRequest request) {
        commentService.addComment(id, request.userId(), request.comentario());
        return ResponseEntity.ok("Comentario publicado correctamente.");
    }

    @GetMapping
    public ResponseEntity<?> getAllPublications() {
        return ResponseEntity.ok(publicationService.getAllPublications());
    }

}
