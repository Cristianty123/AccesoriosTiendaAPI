package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.tienda.accesorios.accesoriostiendaapi.dto.LoginRequestDTO;
import com.tienda.accesorios.accesoriostiendaapi.dto.LoginResponseDTO;
import com.tienda.accesorios.accesoriostiendaapi.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacion")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/validacion")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }
}
