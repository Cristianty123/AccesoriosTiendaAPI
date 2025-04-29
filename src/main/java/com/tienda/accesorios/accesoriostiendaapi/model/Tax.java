package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tax")
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double value;

    private String description;

    @Column(nullable = false)
    private boolean ispercentage;

    public Tax() {}

    public Tax(String name, Double value, String description, boolean ispercentage) {
        this.name = name;
        this.value = value;
        this.description = description;
        this.ispercentage = ispercentage;
    }

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIspercentage() {
        return ispercentage;
    }

    public void setIspercentage(boolean ispercentage) {
        this.ispercentage = ispercentage;
    }
}
