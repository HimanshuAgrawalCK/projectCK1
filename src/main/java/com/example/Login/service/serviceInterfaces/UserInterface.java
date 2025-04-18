package com.example.Login.service.serviceInterfaces;

import com.example.Login.dto.JwtResponse;
import com.example.Login.dto.LoginRequestDTO;
import com.example.Login.dto.UserDTO;
import com.example.Login.dto.UserSummary;
import com.example.Login.exceptionhandler.UserAlreadyExists;
import com.example.Login.exceptionhandler.UserNotFoundException;
import com.example.Login.exceptionhandler.WrongPasswordException;
import com.example.Login.exceptionhandler.WrongRoleException;
import org.springframework.data.domain.Page;

public interface UserInterface {

    JwtResponse login(LoginRequestDTO loginRequestDTO) throws WrongPasswordException;

    UserDTO addUser(UserDTO userDTO, String token) throws UserAlreadyExists, WrongRoleException;

    Page<UserSummary> getAllUsers(int page, int size, String token);

    void logout(String token);

    UserDTO getUser(Long id) throws UserNotFoundException;

    UserDTO updateUser(UserDTO userDTO, Long id);
}
