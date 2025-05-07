package com.api.musiconnect.service;

import com.api.musiconnect.dto.request.CreateUserRequest;
import com.api.musiconnect.dto.response.UserResponse;
import com.api.musiconnect.exception.DuplicateResourceException;
import com.api.musiconnect.exception.ResourceNotFoundException;
import com.api.musiconnect.mapper.UserMapper;
import com.api.musiconnect.model.entity.User;
import com.api.musiconnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Registrar usuario
    @Transactional
    public UserResponse registerUser(CreateUserRequest request)
    {
        // Saber si el usuario con dichos datos ya está en la base de datos
        if (userRepository.existsUserByEmail(request.email()))
        {
            throw new DuplicateResourceException("El usuario con dicho email ya existe.");
        }
        if (userRepository.existsUserByUsername(request.username()))
        {
            throw new DuplicateResourceException("El usuario con dicho username ya existe.");
        }

        User user = userMapper.UserRequestToUser(request);

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);

        userRepository.save(user);

        return userMapper.UserToUserResponse(user);
    }

    // Mostrar todos los usuarios
    @Transactional
    public List<UserResponse> getAllUsers()
    {
        List<User> users = userRepository.findAll();
        return userMapper.UsersToUsersResponse(users);
    }

    // Buscar usuario según su username
    @Transactional
    public UserResponse getUserByUsername(String username)
    {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con username " + username + " no fue encontrado."));
        return userMapper.UserToUserResponse(user);
    }

    // Actualizar usuario según su id
    @Transactional
    public UserResponse updateUserById(Long id, CreateUserRequest request)
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con id " + id + " no existe."));

        User userReq = userMapper.UserRequestToUser(request);

        user.setEmail(userReq.getEmail());
        user.setUsername(userReq.getUsername());
        user.setPassword(userReq.getPassword());
        user.setBirthdate(userReq.getBirthdate());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return userMapper.UserToUserResponse(user);
    }

    // Eliminar usuario segun su id
    @Transactional
    public String deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con id " + id + " no existe."));

        userRepository.delete(user);
        return "El usuario se eliminó exitosamente.";
    }
}
