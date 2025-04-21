package com.api.musiconnect.service;

import com.api.musiconnect.exception.*;
import com.api.musiconnect.model.User;
import com.api.musiconnect.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    // --- OTHER FUNCTIONS ---
    // Si el nombre es válido
    public void isUsernameValid(String username)
    {
        String NAME_REGEX = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";
        Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

        if (username == null || username.trim().isEmpty())
        {
            throw new InvalidInputException("El username no puede estar vacío.");
        }
        if (!NAME_PATTERN.matcher(username).matches())
        {
            throw new InvalidInputException("El username solo puede contener letras, espacios y tildes.");
        }
    }
    // Si el correo es válido
    public void isEmailValid(String email)
    {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,6}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        if (email == null || email.trim().isEmpty())
        {
            throw new InvalidInputException("El correo electrónico no puede estar vacío.");
        }
        if (!EMAIL_PATTERN.matcher(email).matches())
        {
            throw new InvalidInputException("Formato del correo electrónico inválido");
        }
    }
    // Si la contraseña es válida
    public void isPasswordValid(String password)
    {
        String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\p{Punct}]).{8,}$";
        Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

        if (password == null || password.trim().isEmpty())
        {
            throw new InvalidInputException("La contraseña no puede estar vacía.");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches())
        {
            throw new InvalidInputException("La contraseña debe tener al menos 8 caracteres, incluyendo una mayúscula, una minúscula, un número y un carácter especial.");
        }
    }


    // --- SERVICES ---
    // Obtener todos los usuarios
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    // Crear usuario
    public User createUser(User user)
    {
        // Validaciones
        // username
        isUsernameValid(user.getUsername());
        if (userRepository.existsByUsername(user.getUsername()))
        {
            throw new InvalidInputException("El username ya existe.");
        }
        // email
        isEmailValid(user.getEmail());
        if (userRepository.existsByEmail(user.getEmail()))
        {
            throw new InvalidInputException("El correo ya existe.");
        }
        // password
        isPasswordValid(user.getPassword());
        // birthdate
        LocalDate today = LocalDate.now();
        Period age = Period.between(user.getBirthdate(), today);
        if (age.getYears() < 18)
        {
            throw new InvalidInputException("El usuario no es mayor de edad.");
        }
        // createdAt
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);

        return userRepository.save(user);
    }

    // Obtener usuario según su id
    public User getUserById(int id)
    {
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado."));
    }

    // Actualizar usuario
    public User updateUser(int id, User updatedUser)
    {
        // Obtener el usuario por su id
        User originalUser = getUserById(id);

        // Validaciones
        // username
        isUsernameValid(updatedUser.getUsername());
        // email
        isEmailValid(updatedUser.getEmail());
        // password
        isPasswordValid(updatedUser.getPassword());

        // birthdate
        LocalDate today = LocalDate.now();
        Period updatedAge = Period.between(updatedUser.getBirthdate(), today);
        Period oldAge = Period.between(originalUser.getBirthdate(), today);
        if (updatedAge.getYears() != oldAge.getYears() && updatedAge.getYears() < 18)
        {
            throw new InvalidInputException("El usuario no es mayor de edad.");
        }

        // Actualizacion de todos los atributos


        return userRepository.save(originalUser);
    }

    // Eliminar usuario
    public String deleteUser(int id)
    {
        if (!userRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Usuario no encontrado.");
        }
        userRepository.deleteById(id);
        return "El usuario ha sido eliminado con éxito.";
    }
}
