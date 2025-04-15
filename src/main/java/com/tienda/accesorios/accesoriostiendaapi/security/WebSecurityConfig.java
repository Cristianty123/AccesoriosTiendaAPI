package com.tienda.accesorios.accesoriostiendaapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF para simplificar pruebas (actívalo en producción si usás cookies)
                .csrf(csrf -> csrf.disable())

                // Configuración de autorización por rutas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/autenticacion/**",
                                "/items/page",
                                "/uploads/**",
                                "/api/**",
                                "/public/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // Configuración de login por formulario
                .formLogin(form -> form
                        .loginPage("/login") // Página de login personalizada (si existe)
                        .defaultSuccessUrl("/home", true) // Redirección tras login exitoso
                        .permitAll()
                );

        return http.build();
    }
}