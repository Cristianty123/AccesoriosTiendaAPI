package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "purchaseorder_discount")
public class PurchaseOrderDiscount {

    @EmbeddedId
    private PurchaseOrderDiscountId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @MapsId("discountId")
    @JoinColumn(name = "discount_id")
    private Discount discount;

    public PurchaseOrderDiscount() {}

    public PurchaseOrderDiscount(PurchaseOrder purchaseOrder, Discount discount) {
        this.purchaseOrder = purchaseOrder;
        this.discount = discount;
        this.id = new PurchaseOrderDiscountId(purchaseOrder.getId(), discount.getId());
    }

    public PurchaseOrderDiscountId getId() {
        return id;
    }

    public void setId(PurchaseOrderDiscountId id) {
        this.id = id;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
