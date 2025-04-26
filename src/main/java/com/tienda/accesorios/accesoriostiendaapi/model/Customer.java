package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String customername;
    private String phone;
    private String address;
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    // Constructors
    public Customer() {}

    public Customer(String customername, String phone, String address, String email) {
        this.customername = customername;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public String getCustomername() {
        return customername;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

}
