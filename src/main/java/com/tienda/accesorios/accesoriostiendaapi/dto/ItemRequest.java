package com.tienda.accesorios.accesoriostiendaapi.dto;

import com.tienda.accesorios.accesoriostiendaapi.model.ItemType;

import java.util.List;

public class ItemRequest {
    private String id;
    private String name;
    private String description;
    private int stock;
    private Double sellingprice;
    private Double purchaseprice;
    private Boolean itemstate;
    private ItemType itemtype;
    private List<Integer> additionalExpenseIds;
    private String imageurl;

    public ItemRequest(String id, String name, String description, int stock, Double sellingprice, Double purchaseprice, Boolean itemstate, ItemType itemtype, List<Integer> additionalExpenseIds, String imageurl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.sellingprice = sellingprice;
        this.purchaseprice = purchaseprice;
        this.itemstate = itemstate;
        this.itemtype = itemtype;
        this.additionalExpenseIds = additionalExpenseIds;
        this.imageurl = imageurl;
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
    public void setSellingprice(Double sellingprice) { this.sellingprice = sellingprice; }

    public Double getPurchaseprice() { return purchaseprice; }
    public void setPurchaseprice(Double purchaseprice) { this.purchaseprice = purchaseprice; }

    public Boolean getItemstate() { return itemstate; }
    public void setItemstate(Boolean itemstate) { this.itemstate = itemstate; }

    public ItemType getItemtype() { return itemtype; }
    public void setItemtype(ItemType itemtype) { this.itemtype = itemtype; }

    public List<Integer> getAdditionalExpenseIds() { return additionalExpenseIds; }
    public void setAdditionalExpenseIds(List<Integer> additionalExpenseIds) { this.additionalExpenseIds = additionalExpenseIds; }

    public String getImageurl() { return imageurl; }
    public void setImageurl(String imageurl) { this.imageurl = imageurl; }
}
