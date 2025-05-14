package com.api.musiconnect.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity
@Table(name = "comentario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    private User author;

    @ManyToOne
    private Publication publication;
}
