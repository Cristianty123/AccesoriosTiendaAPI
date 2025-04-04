package com.tienda.accesorios.accesoriostiendaapi.dto;

import com.tienda.accesorios.accesoriostiendaapi.model.ItemType;

public class ItemResponse {
    private String id;
    private String name;
    private String description;
    private int stock;
    private Double sellingPrice;
    private Double purchasePrice;
    private Boolean itemState;
    private ItemType itemType;
    private String image;  // Imagen en Base64

    public ItemResponse(String id, String name, String description, int stock, Double sellingPrice, Double purchasePrice, Boolean itemState,ItemType itemType,  String picture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.sellingPrice = sellingPrice;
        this.purchasePrice = purchasePrice;
        this.itemState = itemState;
        this.itemType = itemType;
        this.image = picture;
    }

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

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
