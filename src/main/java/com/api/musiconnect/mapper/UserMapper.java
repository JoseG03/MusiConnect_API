package com.api.musiconnect.mapper;

import com.api.musiconnect.dto.request.CreateUserRequest;
import com.api.musiconnect.dto.response.UserResponse;
import com.api.musiconnect.model.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper
{
    // Convertir UserRequest a User
    public User UserRequestToUser(CreateUserRequest request)
    {
        if (request == null)
        {
            return null;
        }

        // Formato de fecha para LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return User.builder()
                .email(request.email())
                .username(request.username())
                .password(request.password())
                .birthdate(LocalDate.parse(request.birthdate(), formatter)) // Convertir de String a LocalDate si es necesario
                .build();
    }

    // Convertir User a UserResponse
    public UserResponse UserToUserResponse(User user)
    {
        if (user == null)
        {
            return null;
        }

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getBirthdate(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // Convertir List<User> a List<UserResponse>
    public List<UserResponse> UsersToUsersResponse(List<User> users)
    {
        if (users == null)
        {
            return null;
        }

        return users.stream()
                .map(this::UserToUserResponse)
                .collect(Collectors.toList());
    }

    // Convertir List<UserRequest> a List<User>
    public List<User> UserRequestsToUsers(List<CreateUserRequest> requests)
    {
        if (requests == null)
        {
            return null;
        }

        return requests.stream()
                .map(this::UserRequestToUser)
                .collect(Collectors.toList());
    }
}
