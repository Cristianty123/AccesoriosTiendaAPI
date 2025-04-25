package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    public String generateToken(User user) {
        // Convertir roles a claims
        List<String> roles = user.getUserType().getRoles().stream()
                .map(role -> "ROLE_" + role.getId())
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", roles) // Añadir roles al token
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException ex) {
            System.out.println("Token expirado: " + ex.getMessage());
            return false;
        } catch (JwtException | IllegalArgumentException ex) {
            System.out.println("Token inválido: " + ex.getMessage());
            return false;
        }
    }
}
