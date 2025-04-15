package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @Column(name = "id")
    private String id;  // LOGIN, DASHBOARD_VIEW (como definiste en SQL)

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
