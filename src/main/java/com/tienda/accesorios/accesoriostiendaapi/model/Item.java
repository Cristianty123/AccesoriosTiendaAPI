package com.tienda.accesorios.accesoriostiendaapi.model;
import jakarta.persistence.*;

@Entity
@Table(name = "item")
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
    @Lob
    @Column(name = "image",columnDefinition = "bytea") // <--- esto asegura que se mapea al campo 'image'
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "itemType", referencedColumnName = "id")
    private ItemType itemType;

    public Item() {}

    public Item(String id, String name, String description, int stock, Double sellingPrice, Double purchasePrice, Boolean itemState, byte[] image, ItemType itemType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.sellingPrice = sellingPrice;
        this.purchasePrice = purchasePrice;
        this.itemState = itemState;
        this.image = image;
        this.itemType = itemType;
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

    public Double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(Double sellingPrice) { this.sellingPrice = sellingPrice; }

    public Double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(Double purchasePrice) { this.purchasePrice = purchasePrice; }

    public Boolean getItemState() { return itemState; }
    public void setItemState(Boolean itemState) { this.itemState = itemState; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }
}
