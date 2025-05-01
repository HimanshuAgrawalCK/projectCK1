package com.example.Login.controller;


import com.example.Login.dto.LoginRequestDTO;
import com.example.Login.dto.UserDTO;
import com.example.Login.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*" , maxAge = 3600)
public class UserController
{
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("register")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.addUser(userDTO,token));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_READONLY')")
    @GetMapping("users")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getAllUsers(page,size,token));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUser(userDTO,id));
    }
}
