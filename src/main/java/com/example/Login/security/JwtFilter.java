package com.example.Login.security;

import com.example.Login.security.jwt.JWTService;
import com.example.Login.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJIaW1hbnNodWFnYXJ3YWxAZ21haWwuY29tIiwiaWF0IjoxNzQzNzg1NDkxLCJleHAiOjE3NDM3ODU1NTF9.FG1Ez0FdNNCQjAtMDsTHz9-H-ZV9HYPcH02_rbsf3Z4

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        try{


            if (authHeader != null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
                System.out.println("TOKEN : " + token);
                email = jwtService.extractEmail(token);
                log.info("Token "+ token + "email " + email);
            }

            if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(email);
                if(jwtService.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    System.out.println(authToken);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException | MalformedJwtException | UsernameNotFoundException e) {
            throw e; // Let GlobalExceptionHandler catch and respond
        } catch (Exception e) {
            throw new RuntimeException("Internal error in JWT Filter", e); // Optional wrap
        }
    }
}
