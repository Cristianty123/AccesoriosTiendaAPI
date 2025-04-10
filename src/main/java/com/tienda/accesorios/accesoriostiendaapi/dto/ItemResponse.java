package com.tienda.accesorios.accesoriostiendaapi.dto;

import com.tienda.accesorios.accesoriostiendaapi.model.ItemType;

public class ItemResponse {
    private String id;
    private String name;
    private String description;
    private int stock;
    private Double sellingprice;
    private Double purchaseprice;
    private ItemType itemtype;
    private boolean free_shipping;
    private Double price_shipping;
    private String imageurl;

    public ItemResponse(String id, String name, String description, int stock, Double sellingprice, Double purchaseprice, ItemType itemtype, boolean free_shipping, Double price_shipping, String imageurl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.sellingprice = sellingprice;
        this.purchaseprice = purchaseprice;
        this.itemtype = itemtype;
        this.free_shipping = free_shipping;
        this.price_shipping = price_shipping;
        this.imageurl = imageurl;
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

    public Double getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(Double sellingprice) {
        this.sellingprice = sellingprice;
    }

    public ItemType getItemtype() {
        return itemtype;
    }

    public void setItemtype(ItemType itemtype) {
        this.itemtype = itemtype;
    }

    public Double getPurchaseprice() {
        return purchaseprice;
    }

    public void setPurchaseprice(Double purchaseprice) {
        this.purchaseprice = purchaseprice;
    }

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

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
