package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "debt")
public class Debt {

    @Id
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "debtaomunt", nullable = false)
    private Double debtaomunt;

    @Column(name = "ispaid", nullable = false)
    private boolean isPaid;

    @Column(name = "debttype_id", nullable = false)
    private Integer debttype_id;

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

    public Double getDebtaomunt() {
        return debtaomunt;
    }

    public void setDebtaomunt(Double debtaomunt) {
        this.debtaomunt = debtaomunt;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Integer getDebttype_id() {
        return debttype_id;
    }

    public void setDebttype_id(Integer debttype_id) {
        this.debttype_id = debttype_id;
    }
}
