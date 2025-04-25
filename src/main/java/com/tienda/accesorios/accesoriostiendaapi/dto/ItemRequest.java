package com.tienda.accesorios.accesoriostiendaapi.dto;

import com.tienda.accesorios.accesoriostiendaapi.model.ItemType;

import java.util.List;

public class ItemRequest {
    private String name;
    private String description;
    private int stock;
    private Double sellingprice;
    private Double purchaseprice;
    private Boolean itemstate;
    private ItemType itemtype;
    private boolean free_shipping;
    private Double price_shipping;
    private List<Integer> additionalExpenseIds;
    private Integer discountId;

    public ItemRequest(String name, String description, int stock, Double sellingprice, Double purchaseprice, Boolean itemstate, ItemType itemtype, boolean free_shipping, Double price_shipping, List<Integer> additionalExpenseIds, Integer discountId) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.sellingprice = sellingprice;
        this.purchaseprice = purchaseprice;
        this.itemstate = itemstate;
        this.itemtype = itemtype;
        this.free_shipping = free_shipping;
        this.price_shipping = price_shipping;
        this.additionalExpenseIds = additionalExpenseIds;
        this.discountId = discountId;
    }

    // Getters y Setters
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

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }
}
