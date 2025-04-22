package com.api.musiconnect.model.dto;

import lombok.Data;

@Data
public class UserReqDTO
{
    private String username;
    private String password;
    private String email;
    private String birthdate;
}
