package com.tienda.accesorios.accesoriostiendaapi.dto;

public class LoginResponseDTO {
    // ... tus campos y getters/setters ...
    private String token;
    private String email;
    private String userId;
    private String userType;
    private String message;
    private boolean success;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    // Método estático para iniciar el builder
    public static LoginResponseDTOBuilder builder() {
        return new LoginResponseDTOBuilder();
    }

    // Clase Builder interna
    public static class LoginResponseDTOBuilder {
        private String token;
        private String email;
        private String userId;
        private String userType;
        private String message;
        private boolean success;

        public LoginResponseDTOBuilder token(String token) {
            this.token = token;
            return this;
        }

        public LoginResponseDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public LoginResponseDTOBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public LoginResponseDTOBuilder userType(String userType) {
            this.userType = userType;
            return this;
        }

        public LoginResponseDTOBuilder message(String message) {
            this.message = message;
            return this;
        }

        public LoginResponseDTOBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public LoginResponseDTO build() {
            LoginResponseDTO dto = new LoginResponseDTO();
            dto.setToken(token);
            dto.setEmail(email);
            dto.setUserId(userId);
            dto.setUserType(userType);
            dto.setMessage(message);
            dto.setSuccess(success);
            return dto;
        }
    }
}
