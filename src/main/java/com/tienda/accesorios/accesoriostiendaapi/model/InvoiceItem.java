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

    // --- Constructores ---
    public InvoiceItem() {
    }

    public InvoiceItem(Invoice invoice, Item item) {
        this.invoice = invoice;
        this.item = item;
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

}
