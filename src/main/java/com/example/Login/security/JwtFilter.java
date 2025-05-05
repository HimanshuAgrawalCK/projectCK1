package com.example.Login.security;

import com.example.Login.exceptionhandler.InvalidTokenException;
import com.example.Login.exceptionhandler.UserNotFoundException;
import com.example.Login.repository.BlacklistedTokenRepository;
import com.example.Login.security.jwt.JWTService;
import com.example.Login.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Autowired
    BlacklistedTokenRepository blacklistedTokenRepository;

    @Value("${jwt.key}")
    private String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws InvalidTokenException, ExpiredJwtException, UserNotFoundException,
            MalformedJwtException, IOException, ServletException {

        try{
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String email = null;


            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                log.info("TOKENSS : " + token);
                email = jwtService.extractEmail(token);
                log.info("Token " + token + "email " + email);
            }

            if (token != null && blacklistedTokenRepository.existsByToken("Bearer " + token)) {
                throw new InvalidTokenException("Please login again!");
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(email);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    log.info("In Do filter "+ authToken);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (ExpiredJwtException | MalformedJwtException | UsernameNotFoundException |
               InvalidTokenException | SignatureException ex) {

            log.error("JWT Error: {}", ex.getMessage());
            log.error(ex.getClass().toString());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            if(ex instanceof ExpiredJwtException){

                response.getWriter().write("Session Expired, Please login again");
            }
            else{
                response.getWriter().write(ex.getMessage());
            }
        } catch (Exception e) {
            log.error("Error occurred " + e.getMessage());
        }
    }
}
