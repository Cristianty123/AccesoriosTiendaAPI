package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "userstate")
public class UserState {
    @Id
    @Column(name = "id")
    private String id;  // ACTIVE, INACTIVE, SUSPENDED (como definiste en SQL)

    @Column(name = "name", nullable = false)
    private String name;

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
}