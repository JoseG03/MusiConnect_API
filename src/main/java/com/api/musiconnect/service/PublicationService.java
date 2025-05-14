package com.api.musiconnect.service;

import com.api.musiconnect.exception.ResourceNotFoundException;
import com.api.musiconnect.model.entity.Publication;
import com.api.musiconnect.model.entity.User;
import com.api.musiconnect.repository.PublicationRepository;
import com.api.musiconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PublicationService {

    private final PublicationRepository publicationRepo;
    private final UserRepository userRepo;

    public Long createPublication(String content, Long userId) {
        User author = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Publication pub = new Publication();
        pub.setContent(content);
        pub.setAuthor(author);
        pub.setCreatedAt(LocalDateTime.now());

        return publicationRepo.save(pub).getId();
    }

    public void editPublication(Long pubId, Long userId, String newContent) {
        Publication pub = publicationRepo.findById(pubId)
                .orElseThrow(() -> new ResourceNotFoundException("Publication not found"));

        if (!pub.getAuthor().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to edit this publication.");
        }

        pub.setContent(newContent);
        publicationRepo.save(pub);
    }


    public Object getAllPublications() {
        return publicationRepo.findAll();
    }
}

//gurt: yo

