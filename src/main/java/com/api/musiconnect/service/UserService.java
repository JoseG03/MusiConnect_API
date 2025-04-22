package com.api.musiconnect.service;

import com.api.musiconnect.exception.*;
import com.api.musiconnect.mapper.UserMapper;
import com.api.musiconnect.model.dto.UserReqDTO;
import com.api.musiconnect.model.dto.UserResDTO;
import com.api.musiconnect.model.entity.User;
import com.api.musiconnect.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
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
    // Si la fecha de nacimiento es de un mayor de edad
    public void isBirthdateValid(LocalDate birthdate)
    {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //LocalDate _birthdate = LocalDate.parse(birthdate, formatter);
        LocalDate now = LocalDate.now();

        if (Period.between(birthdate, now).getYears() < 18)
        {
            throw new InvalidInputException("El usuario es menor de edad.");
        }
    }
    // Si el username es repetido
    public void isUsernameDuplicated(String username, List<User> list)
    {
        for (User user : list)
        {
           if (user.getUsername().equals(username))
           {
               throw new InvalidInputException("Ya existe un usuario con ese username.");
           }
        }
    }
    // Si el email es repetido
    public void isEmailDuplicated(String email, List<User> list)
    {
        for (User user: list)
        {
            if (user.getEmail().equals(email))
            {
                throw new InvalidInputException("Ya existe un usuario con ese email.");
            }
        }
    }

    // --- SERVICES ---
    // Obtener todos los usuarios
    @Transactional
    public List<UserResDTO> getAllUsers()
    {
        List<User> users = userRepository.findAll();
        return userMapper.convertListUsertoListUserResDTO(users);
    }

    // Registrar usuario
    @Transactional
    public UserResDTO registerUser(UserReqDTO userReqDTO)
    {
        // Convertir: UserReqDTO -> User
        User userReq = userMapper.convertUserReqDTOtoUser(userReqDTO);

        // Validaciones:
        // 1. Formato correcto
        isUsernameValid(userReq.getUsername());
        isPasswordValid(userReq.getPassword());
        isEmailValid(userReq.getEmail());
        isBirthdateValid(userReq.getBirthdate());
        // 2. Valores repetidos
        if (userRepository.existsByUsername(userReq.getUsername()))
        {
            throw new InvalidInputException("Ya existe un usuario con ese username.");
        }
        if (userRepository.existsByEmail(userReq.getEmail()))
        {
            throw new InvalidInputException("Ya existe un usuario con ese email.");
        }

        // Guardar y responder
        userReq.setCreatedAt(LocalDateTime.now());
        userRepository.save(userReq);
        return userMapper.convertUserToUserResDTO(userReq);
    }

    // Obtener usuario según su id
    @Transactional
    public UserResDTO getUserById(Long id)
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe."));
        return userMapper.convertUserToUserResDTO(user);
    }

    // Actualizar usuario
    @Transactional
    public UserResDTO updateUserById(Long id, UserReqDTO userReqDTO)
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe."));
        User userReq = userMapper.convertUserReqDTOtoUser(userReqDTO);

        // Validaciones
        // 1. Formato correcto
        isUsernameValid(userReq.getUsername());
        isPasswordValid(userReq.getPassword());
        isEmailValid(userReq.getEmail());
        isBirthdateValid(userReq.getBirthdate());
        // 3. Son los mismos datos?
        if (user.getUsername().equals(userReq.getUsername())
                && user.getPassword().equals(userReq.getPassword())
                && user.getEmail().equals(userReq.getEmail())
                && user.getBirthdate().isEqual(userReq.getBirthdate()))
        {
            return userMapper.convertUserToUserResDTO(user);
        }

        // 2. Valores repetidos
        // Eliminar el usuario segun su id de la lista
        List<User> usersList = userRepository.findAll();
        for (int i = 0; i < usersList.size(); i++)
        {
            // Eliminamos de la lista el usuario del que vamos a actualizar
            if (usersList.get(i).getId().equals(id))
            {
                usersList.remove(i);
                break;
            }
        }
        isUsernameDuplicated(userReq.getUsername(), usersList);
        isEmailDuplicated(userReq.getEmail(), usersList);

        // Guardar y responder
        user.setUsername(userReq.getUsername());
        user.setPassword(userReq.getPassword());
        user.setEmail(userReq.getEmail());
        user.setBirthdate(userReq.getBirthdate());
        user.setUpdatedAt(LocalDateTime.now()); // Actualizamos a la fecha actual
        userRepository.save(user);
        return userMapper.convertUserToUserResDTO(user);
    }

    // Eliminar usuario
    @Transactional
    public String deleteUserById(Long id)
    {
        if (!userRepository.existsById(id))
        {
            throw new ResourceNotFoundException("El usuario no existe.");
        }

        userRepository.deleteById(id);
        return "El usuario ha sido eliminado.";
    }
}
