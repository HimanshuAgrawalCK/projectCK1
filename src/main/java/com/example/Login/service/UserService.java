package com.example.Login.service;

import com.example.Login.dto.JwtResponse;
import com.example.Login.dto.LoginRequestDTO;
import com.example.Login.dto.UserDTO;
import com.example.Login.dto.UserSummary;
import com.example.Login.entity.BlacklistedTokens;
import com.example.Login.entity.ERole;
import com.example.Login.entity.Role;
import com.example.Login.entity.User;
import com.example.Login.exceptionhandler.UserAlreadyExists;
import com.example.Login.exceptionhandler.WrongPasswordException;
import com.example.Login.exceptionhandler.WrongRoleException;
import com.example.Login.repository.BlacklistedTokenRepository;
import com.example.Login.repository.RoleRepository;
import com.example.Login.repository.UserRepository;
import com.example.Login.security.jwt.JWTService;
import com.example.Login.utils.DTOtoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class UserService {

    @Autowired
    JWTService jwtService;

    @Autowired
    BlacklistedTokenRepository blacklistedTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ObjectMapper objectMapper;

    public JwtResponse UserAuth(LoginRequestDTO loginRequestDTO) throws WrongPasswordException {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken
                        (loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).get();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            String val = "User verified";
            return new JwtResponse(user.getName(),
                    jwtService.generateToken(loginRequestDTO.getEmail()),
                    user.getId(),
                    user.getEmail(),
                    user.getRole().getRole().name(),
                    val,
                    HttpStatus.OK.value());
        }
        throw new BadCredentialsException("Bad Credentials");
//        return new JwtResponse("After auth method in login service",null,null,null,null,"Not Authenticated",401);
    }

    public UserDTO addUser(UserDTO userDTO, String token) throws UserAlreadyExists, WrongRoleException {

        System.out.println("Hello");
        String actualToken = token.substring(7);
        String email = jwtService.extractEmail(actualToken);

        User userCheck = userRepository.findByEmail(email).get();

        if (!(userCheck.getRole().getRole() == ERole.ADMIN)) {
            log.info("ROLE IS : " + userCheck.getRole().getRole().name());
            throw new WrongRoleException("Access Denied");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExists();
        }
        User user = DTOtoEntity.map(userDTO);
        ERole roleEnum = ERole.valueOf(userDTO.getRole().name().toUpperCase());
        Role role = roleRepository.findByRole(roleEnum).orElseThrow(() -> {
            throw new WrongRoleException("Access Denied");
        });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        userRepository.save(user);
        userDTO.setId(role.getId());
        return userDTO;
    }



    public Page<UserSummary> getAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllUserSummaries(pageable);

//        List<User> users = userRepository.findAll();
//        return users;
    }

    public void logout(String token) {
//        System.out.println(request.getHeaders("Authorization"));
//        BlacklistedTokens tokens =  request.getHeaders("Authorization");
        System.out.println("Token : " + token);
        BlacklistedTokens blacklistedTokens = new BlacklistedTokens();
        blacklistedTokens.setToken(token);
        blacklistedTokenRepository.save(blacklistedTokens);

    }
}
