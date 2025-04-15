package com.tienda.accesorios.accesoriostiendaapi;

public class PasswordHasher {
    public static void main(String[] args) {
        System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("Admin1234"));
    }
}

