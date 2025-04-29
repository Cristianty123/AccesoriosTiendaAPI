package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "purchaseorder_item")
public class PurchaseOrderItem {

    @EmbeddedId
    private PurchaseOrderItemId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    public PurchaseOrderItem() {}

    public PurchaseOrderItem(PurchaseOrder purchaseOrder, Item item) {
        this.purchaseOrder = purchaseOrder;
        this.item = item;
        this.id = new PurchaseOrderItemId(purchaseOrder.getId(), item.getId());
    }

    public PurchaseOrderItemId getId() {
        return id;
    }

    public void setId(PurchaseOrderItemId id) {
        this.id = id;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
