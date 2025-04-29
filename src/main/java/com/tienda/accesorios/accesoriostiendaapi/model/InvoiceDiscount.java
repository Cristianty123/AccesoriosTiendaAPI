package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "invoice_discount")
public class InvoiceDiscount {

    @EmbeddedId
    private InvoiceDiscountId id;

    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @MapsId("discountId")
    @JoinColumn(name = "discount_id")
    private Discount discount;

    // Constructor vacío
    public InvoiceDiscount() {}

    public InvoiceDiscount(Invoice invoice, Discount discount) {
        this.invoice = invoice;
        this.discount = discount;
        this.id = new InvoiceDiscountId(invoice.getId(), discount.getId());
    }

    // Getters y setters

    public InvoiceDiscountId getId() {
        return id;
    }

    public void setId(InvoiceDiscountId id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
