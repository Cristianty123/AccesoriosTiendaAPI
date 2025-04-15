package com.tienda.accesorios.accesoriostiendaapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {
    @NotBlank(message = "El email es requerido")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    public @NotBlank(message = "El email es requerido") @Email(message = "Debe ser un email válido") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "El email es requerido") @Email(message = "Debe ser un email válido") String email) {
        this.email = email;
    }

    public @NotBlank(message = "La contraseña es requerida") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "La contraseña es requerida") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password) {
        this.password = password;
    }
}