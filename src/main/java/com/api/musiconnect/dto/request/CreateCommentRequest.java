package com.api.musiconnect.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest (

        @NotNull(message = "El usuario es obligatorio.")
        Long userId,

        @NotBlank(message = "El comentario no puede estar vacío ni superar los 300 caracteres.")
        @Size(max = 300, message = "El comentario no puede estar vacío ni superar los 300 caracteres.")
        String comentario

) {

}
