package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.dto.LoginRequestDTO;
import com.tienda.accesorios.accesoriostiendaapi.dto.LoginResponseDTO;
import com.tienda.accesorios.accesoriostiendaapi.exception.AuthenticationException;
import com.tienda.accesorios.accesoriostiendaapi.model.User;
import com.tienda.accesorios.accesoriostiendaapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        // Validación adicional
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            throw new AuthenticationException("Email y contraseña son requeridos");
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthenticationException("Credenciales inválidas"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Credenciales inválidas");
        }

        if (!"ACTIVE".equals(user.getUserState().getId())) {
            throw new AuthenticationException("Usuario no está activo. Contacte al administrador");
        }

        String token = jwtTokenService.generateToken(user);

        return LoginResponseDTO.builder()
                .message("Autenticación exitosa")
                .token(token)
                .email(user.getEmail())
                .userId(user.getId())
                .userType(user.getUserType().getId())
                .success(true)
                .build();
    }
}