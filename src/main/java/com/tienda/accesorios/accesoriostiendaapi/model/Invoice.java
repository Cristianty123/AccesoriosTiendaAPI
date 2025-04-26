package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(columnDefinition = "text")
    private String enterprisedata;

    private LocalDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double subtotal;
    private Double total;

    // Relación con ítems
    @ManyToMany
    @JoinTable(
            name = "invoice_item",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

}
