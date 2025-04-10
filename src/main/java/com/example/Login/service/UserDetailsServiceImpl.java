package com.example.Login.service;

import com.example.Login.entity.User;
import com.example.Login.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("No User is associated with this email"));

        return UserDetailsImpl.build(user);
    }
    private List<GrantedAuthority> getAuthorities(User user){
        return List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole().getRole().name()));
    }
}
