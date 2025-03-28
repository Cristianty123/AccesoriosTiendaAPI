package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int stock;

    private Double sellingPrice;
    private Double purchasePrice;
    private Boolean itemState;
    private String picture;

    public Item() {}

    public Item(String id, String name, String description, int stock, Double sellingPrice, Double purchasePrice, Boolean itemState, String picture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.sellingPrice = sellingPrice;
        this.purchasePrice = purchasePrice;
        this.itemState = itemState;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Boolean getItemState() {
        return itemState;
    }

    public void setItemState(Boolean itemState) {
        this.itemState = itemState;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
