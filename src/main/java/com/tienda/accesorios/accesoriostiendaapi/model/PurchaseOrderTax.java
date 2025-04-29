package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "purchaseorder_tax")
public class PurchaseOrderTax {

    @EmbeddedId
    private PurchaseOrderTaxId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @MapsId("taxId")
    @JoinColumn(name = "tax_id")
    private Tax tax;

    public PurchaseOrderTax() {}

    public PurchaseOrderTax(PurchaseOrder purchaseOrder, Tax tax) {
        this.purchaseOrder = purchaseOrder;
        this.tax = tax;
        this.id = new PurchaseOrderTaxId(purchaseOrder.getId(), tax.getId());
    }

    public PurchaseOrderTaxId getId() {
        return id;
    }

    public void setId(PurchaseOrderTaxId id) {
        this.id = id;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }
}
