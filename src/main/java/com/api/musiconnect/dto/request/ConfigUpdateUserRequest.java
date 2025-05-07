package com.api.musiconnect.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ConfigUpdateUserRequest(

        @NotBlank(message = "El tipo de usuario no puede estar vacío.")
        @Pattern(regexp = "^(ADMIN|ARTIST|PRODUCER)$", message = "El tipo de usuario debe ser ADMIN o USER.")
        String userType,

        @NotBlank(message = "El email no puede estar vacío.")
        @Email(message = "El email debe tener un formato válido.")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía.")
        @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,50}$",
                message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.")
        String password,

        @NotBlank(message = "El nombre de usuario no puede estar vacío.")
        @Size(min = 3, max = 30, message = "El nombre de usuario debe tener entre 3 y 30 caracteres.")
        String username,

        @NotBlank(message = "La fecha de nacimiento no puede estar vacía.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "El formato de fecha debe ser yyyy-MM-dd.")
        String birthdate,

        @NotBlank(message = "El género no puede estar vacío.")
        @Pattern(regexp = "^(MALE|FEMALE|INDETERMINATE)$", message = "El género debe ser MALE, FEMALE o INDETERMINATE.")
        String gender,

        String bio,
        String location,

        @NotBlank(message = "El estado de disponibilidad no puede estar vacío.")
        @Pattern(regexp = "^(ONLINE|AWAY|DO_NOT_DISTURB|INVISIBLE)$", message = "El estado de disponibilidad debe ser ONLINE, AWAY, DO_NOT_DISTURB o INVISIBLE.")
        String status
)
{}
