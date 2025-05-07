package com.api.musiconnect.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = "email", name = "uk_user_email"),
                @UniqueConstraint(columnNames = "username", name = "uk_user_username")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;
}
