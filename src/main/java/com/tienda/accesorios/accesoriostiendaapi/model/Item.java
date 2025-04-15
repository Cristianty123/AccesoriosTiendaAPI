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

    @Column(name = "created_at", nullable = false, insertable = false, updatable=false)
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

    private boolean free_shipping;
    private Double price_shipping;

    public Item() {}

    public Item(String id, String name, int stock, String description, Double purchaseprice, Double sellingprice, Boolean itemstate, String imageurl, ItemType itemtype, boolean free_shipping, Double price_shipping) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.description = description;
        this.purchaseprice = purchaseprice;
        this.sellingprice = sellingprice;
        this.itemstate = itemstate;
        this.imageurl = imageurl;
        this.itemtype = itemtype;
        this.free_shipping = free_shipping;
        this.price_shipping = price_shipping;
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

    public String getimageurl() { return imageurl; }
    public void setimageurl(String imageUrl) { this.imageurl = imageUrl; }

    public ItemType getItemtype() { return itemtype; }
    public void setItemtype(ItemType itemType) { this.itemtype = itemType; }

    public boolean isFree_shipping() {
        return free_shipping;
    }

    public void setFree_shipping(boolean free_shipping) {
        this.free_shipping = free_shipping;
    }

    public Double getPrice_shipping() {
        return price_shipping;
    }

    public void setPrice_shipping(Double price_shipping) {
        this.price_shipping = price_shipping;
    }
}
