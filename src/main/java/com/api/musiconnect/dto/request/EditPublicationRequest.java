package com.api.musiconnect.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EditPublicationRequest(

        @NotNull(message = "El usuario es obligatorio.")
        Long userId,

        @NotBlank(message = "El contenido no puede estar vacío ni superar los 500 caracteres.")
        @Size(max = 500, message = "El contenido no puede estar vacío ni superar los 500 caracteres.")
        String content

) {}
