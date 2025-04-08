package com.tienda.accesorios.accesoriostiendaapi.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "item")
public class Item {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int stock;

    private Double sellingprice;
    private Double purchaseprice;
    private Boolean itemstate;

    @Column(name = "imageurl")
    private String imageurl;

    @ManyToOne
    @JoinColumn(name = "itemtype", referencedColumnName = "id")
    private ItemType itemtype;

    public Item() {}

    public Item(String id, String name, String description, int stock, Double sellingprice, Double purchaseprice, Boolean itemstate, String imageurl, ItemType itemtype) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.sellingprice = sellingprice;
        this.purchaseprice = purchaseprice;
        this.itemstate = itemstate;
        this.imageurl = imageurl;
        this.itemtype = itemtype;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Double getSellingprice() { return sellingprice; }
    public void setSellingprice(Double sellingPrice) { this.sellingprice = sellingPrice; }

    public Double getPurchaseprice() { return purchaseprice; }
    public void setPurchaseprice(Double purchasePrice) { this.purchaseprice = purchasePrice; }

    public Boolean getItemstate() { return itemstate; }
    public void setItemstate(Boolean itemState) { this.itemstate = itemState; }

    public String getimageUrl() { return imageurl; }
    public void setimageUrl(String imageUrl) { this.imageurl = imageUrl; }

    public ItemType getItemtype() { return itemtype; }
    public void setItemtype(ItemType itemType) { this.itemtype = itemType; }
}
