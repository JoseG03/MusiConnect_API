package com.api.musiconnect.service;
import com.api.musiconnect.exception.ResourceNotFoundException;
import com.api.musiconnect.model.entity.Comment;
import com.api.musiconnect.model.entity.Publication;
import com.api.musiconnect.model.entity.User;
import com.api.musiconnect.repository.CommentRepository;
import com.api.musiconnect.repository.PublicationRepository;
import com.api.musiconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PublicationRepository pubRepo;

    public Comment addComment(Long pubId, Long userId, String content) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Publication pub = pubRepo.findById(pubId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion no encontrada"));

        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setPublication(pub);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepo.save(comment);
    }
}
