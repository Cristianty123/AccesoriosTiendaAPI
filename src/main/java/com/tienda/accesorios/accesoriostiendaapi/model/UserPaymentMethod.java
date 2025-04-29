package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_payment_method")
public class UserPaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    private String provider;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "expires_at")
    private LocalDate expiresAt;

    @Column(name = "is_default")
    private boolean isDefault;

    public UserPaymentMethod() {}

    public UserPaymentMethod(Users users, String paymentType, String provider, String accountNumber, LocalDate expiresAt, boolean isDefault) {
        this.users = users;
        this.paymentType = paymentType;
        this.provider = provider;
        this.accountNumber = accountNumber;
        this.expiresAt = expiresAt;
        this.isDefault = isDefault;
    }

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDate expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
