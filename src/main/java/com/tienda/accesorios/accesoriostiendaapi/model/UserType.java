package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "usertype")
public class UserType {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usertype_role",
            joinColumns = @JoinColumn(name = "usertype_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
