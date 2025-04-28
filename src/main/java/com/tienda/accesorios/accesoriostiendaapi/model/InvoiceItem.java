package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem {

    @EmbeddedId
    private InvoiceItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("invoiceId") // conecta el campo invoiceId de InvoiceItemId
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId") // conecta el campo itemId de InvoiceItemId
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double unit_price;

    @Column(nullable = false)
    private Double subtotal;

    // --- Constructores ---
    public InvoiceItem() {
    }

    public InvoiceItem(Invoice invoice, Item item, Integer quantity, Double unitPrice, Double subtotal) {
        this.invoice = invoice;
        this.item = item;
        this.quantity = quantity;
        this.unit_price = unitPrice;
        this.subtotal = subtotal;
        this.id = new InvoiceItemId(invoice.getId(), item.getId());
    }

    public InvoiceItemId getId() {
        return id;
    }

    public void setId(InvoiceItemId id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
