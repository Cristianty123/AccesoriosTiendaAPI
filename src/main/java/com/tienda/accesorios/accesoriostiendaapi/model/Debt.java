package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "debt")
public class Debt {

    @Id
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "debtamount", nullable = false)
    private Double debtamount;

    @Column(name = "ispaid", nullable = false)
    private boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "debttype_id", nullable = false)
    private DebtType debttype;

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

    public Double getDebtamount() {
        return debtamount;
    }

    public void setDebtamount(Double debtamount) {
        this.debtamount = debtamount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public DebtType getDebttype() {
        return debttype;
    }

    public void setDebttype(DebtType debttype) {
        this.debttype = debttype;
    }
}
