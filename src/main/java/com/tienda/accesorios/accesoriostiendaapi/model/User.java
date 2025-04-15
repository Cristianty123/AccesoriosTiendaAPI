package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "\"User\"") // Debido a que el nombre es reservado
public class User {

    @Id
    private String id;

    @Column
    private String email;

    @Column
    private String password;

    // Suponiendo que tengas un mapeo a usertype y que éste tenga la relación con roles.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usertype_id", nullable = false)
    private UserType usertype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }
}
