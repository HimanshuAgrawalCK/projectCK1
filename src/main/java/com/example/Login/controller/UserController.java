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


    @GetMapping
    public String getCall(){
        return "This is login Controller";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("register")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token){
        System.out.println("Hello");
        return ResponseEntity.ok(userService.addUser(userDTO,token));
    }


    @GetMapping("users")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(userService.getAllUsers(page,size));
    }
}
