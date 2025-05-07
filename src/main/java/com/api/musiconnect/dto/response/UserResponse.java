package com.api.musiconnect.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String username,
        String password,
        LocalDate birthdate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
)
{}
