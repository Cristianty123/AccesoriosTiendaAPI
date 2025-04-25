package com.tienda.accesorios.accesoriostiendaapi.repository;

import com.tienda.accesorios.accesoriostiendaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userType ut LEFT JOIN FETCH ut.roles WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);
}
