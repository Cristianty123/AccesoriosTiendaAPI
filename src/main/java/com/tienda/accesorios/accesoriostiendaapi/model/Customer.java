package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customername", nullable = false)
    private String customername;

    @Column(name = "customerid", nullable = false, unique = true)
    private String customerid;

    @Column(name = "isbusiness", nullable = false)
    private boolean isbusiness;

    @Column(name = "address")
    private String address;

    @Column(name = "customerstate", nullable = false)
    private boolean customerstate;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "identifiertype", nullable = false)
    private String identifiertype;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerPhoneNumber> phoneNumbers;

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer    id) {
        this.id = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public boolean isIsbusiness() {
        return isbusiness;
    }

    public void setIsbusiness(boolean isbusiness) {
        this.isbusiness = isbusiness;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isCustomerstate() {
        return customerstate;
    }

    public void setCustomerstate(boolean customerstate) {
        this.customerstate = customerstate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdentifiertype() {
        return identifiertype;
    }

    public void setIdentifiertype(String identifiertype) {
        this.identifiertype = identifiertype;
    }

    public List<CustomerPhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<CustomerPhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
