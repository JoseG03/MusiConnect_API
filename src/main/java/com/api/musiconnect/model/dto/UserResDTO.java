package com.api.musiconnect.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResDTO
{
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDate birthdate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
