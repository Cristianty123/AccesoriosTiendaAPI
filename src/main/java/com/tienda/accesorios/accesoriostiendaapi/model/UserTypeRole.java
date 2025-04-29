package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usertype_role")
public class UserTypeRole {

    @EmbeddedId
    private UserTypeRoleId id;

    @ManyToOne
    @MapsId("usertypeId")
    @JoinColumn(name = "usertype_id")
    private UserType userType;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    public UserTypeRoleId getId() {
        return id;
    }

    public void setId(UserTypeRoleId id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
