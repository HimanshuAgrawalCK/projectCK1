package com.example.Login.service;

import com.example.Login.dto.*;
import com.example.Login.entity.*;
import com.example.Login.exceptionhandler.UserAlreadyExists;
import com.example.Login.exceptionhandler.UserNotFoundException;
import com.example.Login.exceptionhandler.WrongPasswordException;
import com.example.Login.exceptionhandler.WrongRoleException;
import com.example.Login.repository.AwsAccountsRepository;
import com.example.Login.repository.BlacklistedTokenRepository;
import com.example.Login.repository.RoleRepository;
import com.example.Login.repository.UserRepository;
import com.example.Login.security.jwt.JWTService;
import com.example.Login.utils.DTOtoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Login.service.serviceInterfaces.UserInterface;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService implements UserInterface {

    @Autowired
    private DTOtoEntity dtOtoEntity;

    @Autowired
    JWTService jwtService;

    @Autowired
    BlacklistedTokenRepository blacklistedTokenRepository;

    @Autowired
    AwsAccountsRepository awsAccountsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountsService accountsService;

    @Autowired
    AuthenticationManager authenticationManager;

    public JwtResponse login(LoginRequestDTO loginRequestDTO) throws WrongPasswordException {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken
                        (loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Does not exists"));
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            String val = "User verified";


            return new JwtResponse(user.getName(),
                    jwtService.generateToken(loginRequestDTO.getEmail()),
                    user.getId(),
                    user.getEmail(),
                    user.getRole().getRole().name(),
                    val, accountsService.getAccountSummary(user.getId()), user.getLastLoginTime());
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    public UserDTO addUser(UserDTO userDTO, String token) throws UserAlreadyExists, WrongRoleException {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExists();
        }
        User user = dtOtoEntity.map(userDTO);
        Set<AwsAccounts> awsAccounts = userDTO.getAccounts().stream()
                .map(id -> awsAccountsRepository.findByAccountId(id).get())
                .collect(Collectors.toSet());
        user.setAwsAccountsList(awsAccounts);
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


    public Page<UserSummary> getAllUsers(int page, int size, String token) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Optional<User> user = userRepository.findByEmail(jwtService.extractEmail(token.substring(7)));
        return userRepository.findAllUserSummaries(user.get().getId(), pageable);

//        List<User> users = userRepository.findAll();
//        return users;
    }

    public void logout(String token) {
        BlacklistedTokens blacklistedTokens = new BlacklistedTokens();
        blacklistedTokens.setToken(token);
        blacklistedTokenRepository.save(blacklistedTokens);

    }

    public UserDTO getUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User Does Not Exists"));

        UserDTO userResponse = dtOtoEntity.map(user);
        return userResponse;

    }

    public UserDTO updateUser(UserDTO userDTO, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Does Not exist"));

        if (!userDTO.getName().equals(user.getName())) {
            user.setName(userDTO.getName());
        }


        if ((ERole.ADMIN.equals(userDTO.getRole()) || ERole.READONLY.equals(userDTO.getRole()))
                && ERole.CUSTOMER.equals(user.getRole().getRole())) {
            user.setAwsAccountsList(null);
        }

        if (ERole.CUSTOMER.equals(userDTO.getRole()) &&
                (ERole.ADMIN.equals(user.getRole().getRole()) || ERole.READONLY.equals(user.getRole().getRole()))) {
            Set<AwsAccounts> awsAccounts = userDTO.getAccounts()
                    .stream()
                    .map(accountId -> awsAccountsRepository.findByAccountId(accountId).orElseThrow(() ->
                            new RuntimeException("No Accounts Exists : " + accountId)))
                    .collect(Collectors.toSet());
            user.setAwsAccountsList(awsAccounts);
        }
        if (ERole.CUSTOMER.equals(userDTO.getRole()) && ERole.CUSTOMER.equals(user.getRole().getRole())) {
            Set<AwsAccounts> awsAccounts = userDTO.getAccounts()
                    .stream()
                    .map(accountId -> awsAccountsRepository.findByAccountId(accountId).orElseThrow(() ->
                            new RuntimeException("No Accounts Exists : " + accountId)))
                    .collect(Collectors.toSet());
            user.setAwsAccountsList(awsAccounts);
        }
        if (userDTO.getRole() != null &&
                user.getRole() != null &&
                user.getRole().getRole() != null &&
                !userDTO.getRole().equals(user.getRole().getRole())) {
            Role newRole = roleRepository.
                    findByRole(userDTO.getRole()).orElseThrow(() -> new WrongRoleException("ROle Does not exists"));
            user.setRole(newRole);
        }
        userRepository.save(user);
        return dtOtoEntity.map(user);
    }
}
