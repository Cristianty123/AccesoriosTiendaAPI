package com.tienda.accesorios.accesoriostiendaapi.dto;

public class AdditionalExpenseResponse {
    private Integer id;
    private String name;
    private Double expense;
    private String description;

    public AdditionalExpenseResponse(Integer id, String name, Double expense, String description) {
        this.name = name;
        this.id = id;
        this.expense = expense;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }
}
