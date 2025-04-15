package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // serial = auto incremento

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double value;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private boolean ispercentage;

    // Constructor vacío
    public Discount() {}

    // Constructor con todos los campos
    public Discount(Integer id, String name, double value, String description, boolean ispercentage) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.description = description;
        this.ispercentage = ispercentage;
    }

    // Getters y Setters
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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
