package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.dto.LoginRequestDTO;
import com.tienda.accesorios.accesoriostiendaapi.dto.LoginResponseDTO;
import com.tienda.accesorios.accesoriostiendaapi.exception.AuthenticationException;
import com.tienda.accesorios.accesoriostiendaapi.model.User;
import com.tienda.accesorios.accesoriostiendaapi.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenService jwtTokenService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByEmailWithRoles(loginRequest.getEmail())
                    .orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));

            String token = jwtTokenService.generateToken(user);

            return LoginResponseDTO.builder()
                    .message("Autenticación exitosa")
                    .token(token)
                    .email(user.getEmail())
                    .userId(user.getId())
                    .userType(user.getUserType().getId())
                    .success(true)
                    .build();
        } catch (Exception e) {
            throw new AuthenticationException("Credenciales inválidas");
        }
    }
}