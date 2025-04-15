package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.model.Role;
import com.tienda.accesorios.accesoriostiendaapi.model.User;
import com.tienda.accesorios.accesoriostiendaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario por email o nombre de usuario
        Optional<User> userOptional = userRepository.findByEmail(username);

        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado con email: " + username));

        // Convierte la colección de roles a GrantedAuthority
        Collection<GrantedAuthority> authorities = extractAuthorities(user);

        // Retorna el objeto UserDetails (usando la clase propia de Spring Security)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }

    // Método para extraer roles y convertirlos a GrantedAuthorities
    private Collection<GrantedAuthority> extractAuthorities(User user) {
        // Se asume que los roles vienen del userType del usuario
        Set<Role> roles = user.getUsertype().getRoles();
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                .collect(Collectors.toSet());
    }
}
