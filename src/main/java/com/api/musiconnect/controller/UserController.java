package com.api.musiconnect.controller;

import com.api.musiconnect.dto.request.RegisterUserRequest;
import com.api.musiconnect.dto.request.GeneralUpdateUserRequest;
import com.api.musiconnect.dto.response.UserResponse;
import com.api.musiconnect.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request)
    {
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username)
    {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Long id, @Valid @RequestBody GeneralUpdateUserRequest request)
    {
        return new ResponseEntity<>(userService.updateUserById(id, request), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id)
    {
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
    }
}
