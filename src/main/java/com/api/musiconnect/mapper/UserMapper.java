package com.api.musiconnect.mapper;

import com.api.musiconnect.model.dto.UserReqDTO;
import com.api.musiconnect.model.dto.UserResDTO;
import com.api.musiconnect.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper
{
    private final ModelMapper modelMapper;

    // Convertir: User -> UserResDTO
    public UserResDTO convertUserToUserResDTO(User user)
    {
        return modelMapper.map(user, UserResDTO.class);
    }

    // Convertir: UserReqDTO -> User
    public User convertUserReqDTOtoUser(UserReqDTO userReqDTO)
    {
        User user = modelMapper.map(userReqDTO, User.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthdate = LocalDate.parse(userReqDTO.getBirthdate(), formatter);
        user.setBirthdate(birthdate);

        return user;
    }

    // Convertir: List<User> -> List<UserResDTO>
    public List<UserResDTO> convertListUsertoListUserResDTO(List<User> users)
    {
        return users.stream()
                .map(this::convertUserToUserResDTO).collect(Collectors.toList());
    }

    // Convertir: List<UserReqDTO> -> List<User>
    public List<User> convertListResDTOtoListUser(List<UserReqDTO> usersReqDTO)
    {
        return usersReqDTO.stream()
                .map(this::convertUserReqDTOtoUser).collect(Collectors.toList());
    }
}
