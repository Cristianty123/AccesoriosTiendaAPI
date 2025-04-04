package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "additionalexpense")
public class AdditionalExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double expense;

    private String description;

    public AdditionalExpense() {}

    public AdditionalExpense(String name, double expense, String description) {
        this.name = name;
        this.expense = expense;
        this.description = description;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getExpense() { return expense; }
    public void setExpense(double expense) { this.expense = expense; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
