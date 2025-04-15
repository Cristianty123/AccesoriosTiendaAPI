package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.tienda.accesorios.accesoriostiendaapi.dto.ValidateTokenResponseDTO;
import com.tienda.accesorios.accesoriostiendaapi.service.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost")
public class TokenValidationController {

    private final JwtTokenService jwtTokenService;

    public TokenValidationController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/validate-token")
    public ResponseEntity<ValidateTokenResponseDTO> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok(new ValidateTokenResponseDTO(false, "Token no proporcionado"));
        }

        String token = authHeader.substring(7);
        boolean isValid = jwtTokenService.validateToken(token);

        return ResponseEntity.ok(new ValidateTokenResponseDTO(
                isValid,
                isValid ? "Token válido" : "Token inválido o expirado"
        ));
    }
}
