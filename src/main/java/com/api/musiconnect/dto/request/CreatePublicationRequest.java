package com.api.musiconnect.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePublicationRequest {

    @NotNull(message = "El usuario es obligatorio.")
    private Long userId;

    @NotBlank(message = "El contenido no puede estar vacío ni superar los 500 caracteres.")
    @Size(max = 500, message = "El contenido no puede estar vacío ni superar los 500 caracteres.")
    private String content;

    private String tipo;

    // Getters
    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getTipo() {
        return tipo;
    }

    // Setters
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}



