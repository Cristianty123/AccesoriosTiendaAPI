package com.tienda.accesorios.accesoriostiendaapi.dto;

public class ValidateTokenResponseDTO {
    private boolean valid;
    private String message;

    public ValidateTokenResponseDTO() {
    }

    public ValidateTokenResponseDTO(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ValidateTokenResponseDTO{" +
                "valid=" + valid +
                ", message='" + message + '\'' +
                '}';
    }
}

