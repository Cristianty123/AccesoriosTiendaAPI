package com.tienda.accesorios.accesoriostiendaapi.repository;

import com.tienda.accesorios.accesoriostiendaapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.userType ut LEFT JOIN FETCH ut.roles WHERE u.email = :email")
    Optional<Users> findByEmailWithRoles(@Param("email") String email);
}
