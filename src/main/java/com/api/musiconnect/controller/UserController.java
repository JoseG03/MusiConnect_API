package com.api.musiconnect.controller;

import com.api.musiconnect.model.dto.UserReqDTO;
import com.api.musiconnect.model.dto.UserResDTO;
import com.api.musiconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResDTO>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResDTO> getUserById(@PathVariable Long id)
    {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResDTO> registerUser(@RequestBody UserReqDTO user)
    {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserResDTO> updateUserById(@PathVariable Long id, @RequestBody UserReqDTO user)
    {
        return new ResponseEntity<>(userService.updateUserById(id, user), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id)
    {
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
    }
}
