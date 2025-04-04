package com.tienda.accesorios.accesoriostiendaapi.dto;

import com.tienda.accesorios.accesoriostiendaapi.model.ItemType;

import java.util.List;

public class ItemRequest {
    private String id;
    private String name;
    private String description;
    private int stock;
    private Double sellingPrice;
    private Double purchasePrice;
    private Boolean itemState;
    private ItemType itemType;
    private List<Integer> additionalExpenseIds;
    private String image;  // Imagen en Base64

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(Double sellingPrice) { this.sellingPrice = sellingPrice; }

    public Double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(Double purchasePrice) { this.purchasePrice = purchasePrice; }

    public Boolean getItemState() { return itemState; }
    public void setItemState(Boolean itemState) { this.itemState = itemState; }

    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }

    public List<Integer> getAdditionalExpenseIds() { return additionalExpenseIds; }
    public void setAdditionalExpenseIds(List<Integer> additionalExpenseIds) { this.additionalExpenseIds = additionalExpenseIds; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
